package ru.database;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutorDataSetDao {
    private final Connection connection;

    public ExecutorDataSetDao(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T user) {
        List<Field> allFields = getAllFields(user.getClass());
        try {
            String script = String.format("INSERT INTO %1$s.%2$s (%3$s) VALUES (%4$s);",
                    connection.getCatalog(),
                    getDbTable(connection, user.getClass()),
                    getColumnsByFields(allFields),
                    getDbValuesFromDataSetObj(user, allFields));
            DatabaseService.executeUpdate(connection, script);
        } catch (SQLException e) {
            Assertions.fail("SQL exception: " + e.getMessage());
        }
    }

    <T extends DataSet> DataSet load(long id, Class<T> clazz) {
        ResultSet resultSet;
        try {
            String script = String.format("SELECT * FROM %1$s.%2$s WHERE ID = %3$s;",
                    connection.getCatalog(),
                    getDbTable(connection, clazz),
                    id);
            resultSet = DatabaseService.query(connection, script);
            if (!resultSet.next()) {
                return null;
            }
            return createDataSetObj(resultSet, clazz);

        } catch (SQLException e) {
            Assertions.fail("SQL exception: " + e.getMessage());
        }
        throw new AssertionError("Something was wrong!");
    }

    private <T extends DataSet> DataSet createDataSetObj(ResultSet resultSet, Class<T> clazz) {
        List<Field> allFields = getAllFields(clazz);
        final DataSet dataSet;
        try {
            dataSet = clazz.newInstance();

            allFields.forEach(field -> {
                field.setAccessible(true);

                try {
                    if (DataTypesHelper.isStringType(field.getType()))
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

    private <T extends DataSet> String getColumnsByFields(List<Field> allFields) {
        return allFields.stream().filter(field -> !field.getName().equals("id")).map(Field::getName).collect(Collectors.joining(", "));
    }

    private <T extends DataSet> String getDbValuesFromDataSetObj(T user, List<Field> dbColumns) {
        StringBuilder values = new StringBuilder();
        dbColumns.forEach(field -> {
            if (field.getName().equals("id")) return;
            if (!field.isAccessible()) field.setAccessible(true);
            try {
                if (DataTypesHelper.isStringType(field.getType())) {
                    String v = (String) field.get(user);
                    values.append("\"" + v + "\", ");
                } else values.append(field.get(user) + ", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return values.substring(0, values.lastIndexOf(","));
    }

    //Recursive call to get all fields of object
    private static List<Field> getAllFields(Class klass) {
        List<Field> fields = new ArrayList<>(Arrays.asList(klass.getDeclaredFields()));
        if (klass.getSuperclass() != null) {
            fields.addAll(getAllFields(klass.getSuperclass()));
        }
        return fields;
    }

    private <T extends DataSet> String getDbTable(Connection connection, Class<T> clazz) throws SQLException {
        //Предполгаем, что объекты хранятся в таблицах с одноименным названием для класса объекта
        String expectedDbTableName = clazz.getSimpleName();
        if (!DatabaseService.isTableExistInDbByName(connection, expectedDbTableName)) {
            Assertions.fail("Expected table not exist: " + expectedDbTableName);
        }
        return expectedDbTableName;
    }


}
