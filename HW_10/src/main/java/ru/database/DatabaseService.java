package ru.database;

import org.junit.jupiter.api.Assertions;

import java.sql.*;

public class DatabaseService {

    public static void executeUpdate(Connection connection, String sqlScript) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            System.out.println("SQL sqript: " + sqlScript);
            statement.executeUpdate(sqlScript);
        }
    }

    public static ResultSet query(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("SQL sqript: " + query);
        return statement.executeQuery(query);
    }


    public static boolean isTableExistInDbByName(Connection connection, String tableName) throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT 1 FROM  " + tableName;
            result = statement.executeQuery(query);
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
