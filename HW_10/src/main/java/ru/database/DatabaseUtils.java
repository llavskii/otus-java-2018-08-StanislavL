package ru.database;

import org.junit.jupiter.api.Assertions;

import java.sql.*;

public class DatabaseUtils {

    public static void executeUpdate(Connection connection, String sqlScript) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            System.out.println("SQL sqript: " + sqlScript);
            statement.executeUpdate(sqlScript);
        }
    }

    public static void executePrepUpdate(PreparedStatement statement) throws SQLException {
        System.out.println("SQL update sqript: " + getQueryFromPrepareStatement(statement));
        statement.executeUpdate();
    }

    public static ResultSet executePrepQuery(PreparedStatement statement) throws SQLException {
        System.out.println("SQL select sqript: " + getQueryFromPrepareStatement(statement));
        return statement.executeQuery();
    }

    private static String getQueryFromPrepareStatement(PreparedStatement statement) {
        String s = statement.toString();
        return s.substring(s.lastIndexOf("PreparedStatement: "));
    }

    public static boolean isTableExistInDbByName(Connection connection, String tableName) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT 1 FROM  " + tableName;
            statement.executeQuery(query);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public static Connection getConnectionToSqlServer(Driver driver, String urlToDbHost, String dbOptions, String login, String pass) {
        Connection connection = null;
        try {
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(urlToDbHost + dbOptions, login, pass);
        } catch (SQLException e) {
            Assertions.fail("SQL error detected: " + e.getMessage());
        }
        return connection;
    }

    public static Connection getConnectionToDb(String urlToDbHost, String dbName, String dbOptions, String login, String pass) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(urlToDbHost + dbName + dbOptions, login, pass);
        } catch (SQLException e) {
            Assertions.fail("SQL error detected: " + e.getMessage());
        }
        return connection;
    }
}
