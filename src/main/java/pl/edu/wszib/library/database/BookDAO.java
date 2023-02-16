package pl.edu.wszib.library.database;

import pl.edu.wszib.library.entity.Book;
import pl.edu.wszib.library.entity.User;

import java.sql.*;
import java.time.LocalDate;

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

    private void showBook(ResultSet rs) {
        try {
            System.out.print(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    Boolean.parseBoolean(rs.getString("available"))));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showBorrowedBook(ResultSet rs) {
        try {
            showBook(rs);
            System.out.printf("\tuser_name: %s\t user_surname: %s\t" +
                            "date_of_borrow: %s\t date_of_return: %s",
                    rs.getString("user_name"),
                    rs.getString("user_surname"),
                    rs.getDate("date_of_borrow"),
                    rs.getDate("date_of_return"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllBooks() {
        try {
            String sql = "SELECT * FROM tbook";
            String sql2 = "SELECT b.id,b.title,b.author,b.isbn,b.available," +
                    "d.user_name,d.user_surname,d.date_of_borrow,d.date_of_return" +
                    " FROM tbook AS b" +
                    " JOIN borrowedbookdetails AS d ON d.book_id = b.id";
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                switch (rs.getString("available")) {
                    case "true":
                        showBook(rs);
                        System.out.println();
                        break;
                    case "false":
                        rs2.next();
                        showBorrowedBook(rs2);
                        System.out.println();
                        break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllBorrowedBooks() {
        try {
            String sql = "SELECT b.id,b.title,b.author,b.isbn,b.available,d.user_name,d.user_surname,d.date_of_borrow,d.date_of_return" +
                    " FROM tbook AS b" +
                    " JOIN borrowedbookdetails AS d ON d.book_id = b.id";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showBorrowedBook(rs);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllBorrowedOutOfDate(){
        try {
            String sql = "SELECT b.id,b.title,b.author,b.isbn,b.available,d.user_name,d.user_surname,d.date_of_borrow,d.date_of_return" +
                    " FROM tbook AS b" +
                    " JOIN borrowedbookdetails AS d ON d.book_id = b.id" +
                    " WHERE d.date_of_return < ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showBorrowedBook(rs);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findSpecificBook(String userInput){
        try {
            String sql = "SELECT * FROM tbook WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, '%' + userInput.toUpperCase() + '%');
            ps.setString(2, '%' + userInput.toUpperCase() + '%');
            ps.setString(3, '%' + userInput + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showBook(rs);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean searchForBookByID(int id) {
        try {
            String sql = "SELECT * FROM tbook WHERE id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return Boolean.parseBoolean(rs.getString("available"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean borrowBookById(int id, User user) {
        if(searchForBookByID(id)){
            try {
                String sql = "UPDATE tbook SET available = ? WHERE id = ?";
                PreparedStatement ps = this.connection.prepareStatement(sql);
                ps.setString(1, "false");
                ps.setInt(2, id);
                ps.executeUpdate();
                String sql2 = "INSERT INTO borrowedbookdetails (book_id, user_id, user_name, user_surname, date_of_borrow, date_of_return) " +
                        "VALUES (?,?,?,?,?,?)";
                PreparedStatement ps2 = this.connection.prepareStatement(sql2);
                ps2.setInt(1, id);
                ps2.setInt(2, user.getId());
                ps2.setString(3, user.getName());
                ps2.setString(4, user.getSurname());
                ps2.setDate(5, Date.valueOf(LocalDate.now()));
                ps2.setDate(6, Date.valueOf(LocalDate.now().plusDays(14)));
                ps2.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public void addBook(Book book) {
        try {
            String sql = "INSERT INTO tbook (title, author, isbn, avaliable) VALUES (?,?,?,?)";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3,book.getIsbn());
            ps.setString(4,"true");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static BookDAO getInstance() {
        return instance;
    }
}
