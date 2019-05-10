package ru.database;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.sql.Connection;
import java.sql.SQLException;

import static ru.database.DatabaseUtils.executeUpdate;
import static ru.database.DatabaseUtils.getConnectionToSqlServer;

@TestInstance(Lifecycle.PER_CLASS)
public class DataSetDbTest {
    Connection connection = null;

    final String DB_HOST_URL = "jdbc:mysql://localhost:3306/";
    final String DB_OPTIONS = "?useUnicode=true&serverTimezone=UTC";

    final String USER = "root";
    final String PASS = "root";

    final String DB_NAME = "dataDB";
    final String CREATE_DATABASE = "CREATE DATABASE ";
    final String DROP_DATABASE = "DROP DATABASE IF EXISTS ";
    final String CREATE_TABLE_USER_DATASET = "CREATE TABLE userdataset " +
            "(id bigint(20) NOT NULL auto_increment, " +
            "name varchar(255), " +
            "age int(3), " +
            "PRIMARY KEY (id))";

    @BeforeAll
    void createEnvironment() {
        try {
            connection = getConnectionToSqlServer(new com.mysql.cj.jdbc.Driver(), DB_HOST_URL, DB_OPTIONS, USER, PASS);
            executeUpdate(connection, CREATE_DATABASE + DB_NAME);
            connection = DatabaseUtils.getConnectionToDb(DB_HOST_URL, DB_NAME, DB_OPTIONS, USER, PASS);
            executeUpdate(connection, CREATE_TABLE_USER_DATASET);
        } catch (SQLException e) {
            Assertions.fail("SQL error detected: " + e.getMessage());
        }
    }

    @Test
    void ExecutorDataSetDaoTest() throws SQLException, IllegalAccessException {
        ExecutorDataSetDao executorDataSetDao = new ExecutorDataSetDao(connection);

        DataSet userDataSet1 = new UserDataSet(1, "Jack", 35);
        executorDataSetDao.save(userDataSet1);

        DataSet userDataSet2 = new UserDataSet(2, "John", 35);
        executorDataSetDao.save(userDataSet2);

        DataSet that = executorDataSetDao.load(2, UserDataSet.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals(userDataSet2, that),
                () -> Assertions.assertNotEquals(userDataSet1, that)
        );
    }

    @AfterAll
    void tearDown() {
        try {
            executeUpdate(connection, DROP_DATABASE + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
