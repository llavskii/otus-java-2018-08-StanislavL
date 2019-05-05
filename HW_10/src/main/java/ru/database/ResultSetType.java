package ru.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
interface ResultSetType<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
