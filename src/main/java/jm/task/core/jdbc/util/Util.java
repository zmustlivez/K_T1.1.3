package jm.task.core.jdbc.util;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
//    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URI = "jdbc:mysql://localhost:3306/users";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
/*        Connection connection = DriverManager.getConnection(DB_URI, DB_USERNAME, DB_PASSWORD);
        if (connection != null) {
            System.out.println("Successfully connected to MySQL database");
        }
        return connection;*/
        Connection connection = null;
        try {
//            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URI, DB_USERNAME, DB_PASSWORD);
//            System.out.println("Connection ok");
        } catch (SQLException e) {//|ClassNotFoundException
            e.printStackTrace();
            System.err.println("Connection NOK ERROR");
        }
        return connection;
    }
}
