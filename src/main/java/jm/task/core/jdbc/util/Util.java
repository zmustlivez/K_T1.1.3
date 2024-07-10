package jm.task.core.jdbc.util;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_URI = "jdbc:mysql://localhost:3306/users";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123";

    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URI, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection NOK ERROR");
        }
        return connection;
    }
}
