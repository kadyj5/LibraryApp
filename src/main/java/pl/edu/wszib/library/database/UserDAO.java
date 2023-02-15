package pl.edu.wszib.library.database;

import pl.edu.wszib.library.entity.User;

import java.sql.*;

// data access object
public class UserDAO {

    private final Connection connection;
    private static final UserDAO instance = new UserDAO();

    public UserDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library",
                    "root",
                    "");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void userAdd(User user) {
        try {
            String sql = "INSERT INTO tuser (login, password, role, name, surname) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());
            ps.setString(4, user.getName());
            ps.setString(5, user.getSurname());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User findByLogin(String login) {
        try {
            String sql = "SELECT * FROM tuser WHERE login = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);

            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        User.Role.valueOf(resultSet.getString("role")),
                        resultSet.getString("name"),
                        resultSet.getString("surname"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static UserDAO getInstance(){
        return instance;
    }
}
