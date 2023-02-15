package pl.edu.wszib.library.database;

import pl.edu.wszib.library.entity.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
