package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
    }
    // Создание таблицы User(ов)
    public void createUsersTable() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users("
                + "id LONG, " + "name VARCHAR(100) NOT NULL, "
                + "lastName VARCHAR(100) NOT NULL," + "age INT)";
        try (PreparedStatement ps = connection.prepareStatement(createUsers)) {
            connection.setAutoCommit(false);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
// Удаление таблицы User(ов)
    public void dropUsersTable() {
        String dropUsers = "DROP TABLE IF EXISTS Users";
        try (PreparedStatement prs = connection.prepareStatement(dropUsers)) {
            connection.setAutoCommit(false);
            prs.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }

    }
    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO Users (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement prs = connection.prepareStatement(saveUser)) {
            connection.setAutoCommit(false);
            prs.setString(1, name);
            prs.setString(2, lastName);
            prs.setByte(3, age);
            prs.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
   // Удаление User из табицы (по id
    public void removeUserById(long id) {
        String removeUser = "DELETE FROM users WHERE Id = ?";
        try (PreparedStatement prs = connection.prepareStatement(removeUser)) {
            connection.setAutoCommit(false);
            prs.setLong(1, id);
            prs.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
// Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery(sql);
            connection.setAutoCommit(false);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
        return users;
    }
// Очистка содержания таблицы
    public void cleanUsersTable() {
        String clean ="TRUNCATE TABLE users";
        try (PreparedStatement prs = connection.prepareStatement(clean)) {
            connection.setAutoCommit(false);
            prs.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
}
