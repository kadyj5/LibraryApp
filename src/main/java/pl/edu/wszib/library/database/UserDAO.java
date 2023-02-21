package pl.edu.wszib.library.database;

import pl.edu.wszib.library.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    public boolean changeRole(String login) {
        try {
            if (findByLogin(login).isPresent()) {
                User user = (User) findByLogin(login).get();
                if(user.getRole() == User.Role.USER){
                    String sql = "UPDATE tuser SET role = ? WHERE login = ?";
                    PreparedStatement ps = this.connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(User.Role.ADMIN));
                    ps.setString(2, login);
                    ps.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public void userAdd(User user) {
        try {
            String sql = "INSERT INTO tuser (login, password, role, name, surname) VALUES (?,?,?,?,?)";
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());
            ps.setString(4, user.getName());
            ps.setString(5, user.getSurname());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            user.setId(rs.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void listUsers() {
        for(User user : getUsersFromDB()) {
            System.out.println(user);
        }
    }

    public Optional findByLogin(String login) {
        Stream<User> userStream = getUsersFromDB().stream();
        return userStream.filter(user -> user.getLogin()
                .equals(login))
                .findFirst();
    }

    private List<User> getUsersFromDB() {
        try {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM tuser";
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                users.add(new User (resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        User.Role.valueOf(resultSet.getString("role")),
                        resultSet.getString("name"),
                        resultSet.getString("surname")));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDAO getInstance(){
        return instance;
    }
}
