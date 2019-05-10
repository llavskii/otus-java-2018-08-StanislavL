package ru.database;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExecutorDataSetDao {
    private final Connection connection;
    private final String dbName;

        private static Map<Class, List<Field>> dataSetClassesFields = new HashMap<>();
    private static Map<Class, List<Field>> dataSetClassesFilteredFields = new HashMap<>();

    private static Map<Class, PreparedStatement> insertPrepStatements = new HashMap<>();
    private static Map<Class, PreparedStatement> loadPrepStatements = new HashMap<>();

    public ExecutorDataSetDao(Connection connection) throws SQLException {
        this.connection = connection;
        this.dbName = connection.getCatalog();
    }

    public <T extends DataSet> void save(T user) throws SQLException, IllegalAccessException {
        Class clazz = user.getClass();
        PreparedStatement preparedStatement = getSaveStatement(clazz);
        fillValuesSaveStatement(preparedStatement, user);
        DatabaseUtils.executePrepUpdate(preparedStatement);
    }

    public <T extends DataSet> DataSet load(long id, Class<T> clazz) throws SQLException {
        PreparedStatement preparedStatement = getLoadStatement(clazz);
        fillValuesLoadStatement(preparedStatement, id);
        ResultSet resultSet = DatabaseUtils.executePrepQuery(preparedStatement);
        try {
            if (!resultSet.next()) {
                return null;
            }
            return createDataSetObj(resultSet, clazz);
        } catch (SQLException e) {
            Assertions.fail("SQL exception: " + e.getMessage());
        }
        throw new AssertionError("Something was wrong!");
    }

    private void fillValuesLoadStatement(PreparedStatement preparedStatement, long id) throws SQLException {
        preparedStatement.setLong(1, id);
    }

    private <T extends DataSet> PreparedStatement getLoadStatement(Class<T> clazz) throws SQLException {
        if (!loadPrepStatements.containsKey(clazz)) {
            String script = getLoadScriptTemplateDataSetObj(clazz);
            loadPrepStatements.put(clazz, connection.prepareStatement(script));
        }
        return loadPrepStatements.get(clazz);
    }

    private <T extends DataSet> String getLoadScriptTemplateDataSetObj(Class<T> clazz) throws SQLException {
        String script = "SELECT * FROM " + dbName + "." + getDbTable(clazz) + " WHERE ID = ?;";
        System.out.println("Create sql template: " + script);
        return script;
    }

    private <T extends DataSet> PreparedStatement getSaveStatement(Class clazz) throws SQLException {
        if (!insertPrepStatements.containsKey(clazz)) {
            String script = getInsertScriptTemplateDataSetObj(clazz);
            insertPrepStatements.put(clazz, connection.prepareStatement(script));
        }
        return insertPrepStatements.get(clazz);
    }

    private String getInsertScriptTemplateDataSetObj(Class clazz) throws SQLException {
        List<Field> allFields = getFieldsDataSetObj(clazz);
        List<Field> filteredFields = getFilteredFields(clazz, allFields);
        String script = "INSERT INTO " + dbName + "." + getDbTable(clazz) + " (" + getColumnsByFields(filteredFields) + ") " + "VALUES (" + getDbWildcardsForDataSetObj(filteredFields) + ");";
        System.out.println("Create sql template: " + script);
        return script;
    }

    private <T extends DataSet> void fillValuesSaveStatement(PreparedStatement preparedStatement, T user) throws IllegalAccessException, SQLException {
        List<Field> fields = dataSetClassesFilteredFields.get(user.getClass());
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (!field.isAccessible()) field.setAccessible(true);
            if (DataHelper.isStringType(field.getType())) {
                preparedStatement.setString(1 + i, (String) field.get(user));
            } else {
                preparedStatement.setInt(1 + i, (Integer) field.get(user));
            }
        }
    }

    private List<Field> getFieldsDataSetObj(Class clazz) {
        if (!dataSetClassesFields.containsKey(clazz)) dataSetClassesFields.put(clazz, DataHelper.getAllFields(clazz));
        return dataSetClassesFields.get(clazz);
    }

    private List<Field> getFilteredFields(Class clazz, List<Field> allFields) {
        if (!dataSetClassesFilteredFields.containsKey(clazz)) {
            dataSetClassesFilteredFields.put(clazz, allFields.stream().filter(field -> !field.getName().equals("id")).collect(Collectors.toList()));
        }
        return dataSetClassesFilteredFields.get(clazz);
    }

    private <T extends DataSet> String getColumnsByFields(List<Field> allFields) {
        return allFields.stream().map(Field::getName).collect(Collectors.joining(", "));
    }

    private String getDbWildcardsForDataSetObj(List<Field> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            stringBuilder.append("?, ");
        }
        String wildcards = stringBuilder.toString();
        return wildcards.substring(0, wildcards.lastIndexOf(","));
    }

    private <T extends DataSet> DataSet createDataSetObj(ResultSet resultSet, Class<T> clazz) {
        List<Field> allFields = getFieldsDataSetObj(clazz);
        final DataSet dataSet;
        try {
            dataSet = clazz.newInstance();
            allFields.forEach(field -> {
                field.setAccessible(true);
                try {
                    if (DataHelper.isStringType(field.getType()))
                        field.set(dataSet, resultSet.getString(field.getName()));
                    else field.set(dataSet, resultSet.getInt(field.getName()));
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
            });
            return dataSet;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new AssertionError("Something was wrong!");

    }

    private <T extends DataSet> String getDbTable(Class<T> clazz) throws SQLException {
        //Предполагаем, что объекты хранятся в таблицах с одноименным названием для класса объекта
        String expectedDbTableName = clazz.getSimpleName();
        if (!DatabaseUtils.isTableExistInDbByName(connection, expectedDbTableName)) {
            Assertions.fail("Expected table not exist: " + expectedDbTableName);
        }
        return expectedDbTableName;
    }
}
