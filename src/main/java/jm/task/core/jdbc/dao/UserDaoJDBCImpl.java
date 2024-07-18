package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoJDBCImpl.class);

    public UserDaoJDBCImpl() {

    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users ");
            logger.info("Table 'users' dropped successfully.");
//            System.out.println("Table 'Users' delete successfully.");
            // Проверка наличия таблицы после удаления
            boolean tableExists = checkIfTableExists(connection, "users");
            if (!tableExists) {
                logger.info("Table 'users' doesn't exist in the database.");
            } else {
                logger.warn("Table 'users' still exists in the database after deletion!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(255) NOT NULL,"
                + "lastname VARCHAR(255) NOT NULL,"
                + "age TINYINT(3) NOT NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();//Update();
            logger.info("Table 'users' create successfully.");
//            System.out.println("Table 'Users' created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean checkIfTableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, tableName, null);
        return tables.next();
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        String insertSQL = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE id = " + id);
//            System.out.println("RemoveUser with " + id + " delete successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String requireSQL = "SELECT * FROM users";
        try (Connection connection = Util.getConnection();
//             Statement statement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(requireSQL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            System.out.println(users);
//            System.out.println("GetAllUsers take successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
//            System.out.println("Table users clean successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
