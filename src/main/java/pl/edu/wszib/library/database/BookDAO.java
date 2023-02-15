package pl.edu.wszib.library.database;

import pl.edu.wszib.library.entity.Book;

import java.sql.*;

public class BookDAO {

    private final Connection connection;
    private static final BookDAO instance = new BookDAO();

    public BookDAO() {
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
    public void showBooks() {
        try {
            String sql = "SELECT * FROM tbook";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        Boolean.parseBoolean(rs.getString("avaliable"))));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean searchForBookByID(int id) {
        try {
            String sql = "SELECT * FROM tbook WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return Boolean.parseBoolean(rs.getString("avaliable"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean borrowBookById(int id) {
        if(searchForBookByID(id)){
            try {
                String sql = "UPDATE tbook SET avaliable = ? WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, "false");
                ps.setInt(2, id);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    public void searchForSpecificBook(String userInput){
        try {
            String sql = "SELECT * FROM tbook WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, '%' + userInput + '%');
            ps.setString(2, '%' + userInput + '%');
            ps.setString(3, '%' + userInput + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        Boolean.parseBoolean(rs.getString("avaliable"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addBook(Book book) {
        try {
            String sql = "INSERT INTO tbook (title, author, isbn) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3,book.getIsbn());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static BookDAO getInstance() {
        return instance;
    }
}
