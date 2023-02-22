package pl.edu.wszib.library.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.wszib.library.entity.Book;
import pl.edu.wszib.library.entity.User;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookDAOTest {
    BookDAO bookDAO = BookDAO.getInstance();

    @Test
    public void failedAddingBook() {
        Book testBook = new Book();
        Exception exception = assertThrows(NullPointerException.class, () -> bookDAO.addBook(testBook));
        String expectedMessage = "is null";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void failedBorrowBookByID() {
        int id = -1;
        User user = new User(
                1,
                "admin",
                "336284edfa1cdc721ca975e60ae6e2b0",
                User.Role.ADMIN,
                "Karolina",
                "Dyjak"
        );
        Boolean actual = bookDAO.borrowBookById(id, user);
        Boolean expectedResult = false;
        Assertions.assertEquals(expectedResult,actual);
    }

    @Test
    public void failedFindBook() {
        String title = null;
        Exception exception = assertThrows(NullPointerException.class, () -> bookDAO.findSpecificBook(title));
        String expectedMessage = "is null";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void successfulShowingBooksOutOfDate() {
        Assertions.assertDoesNotThrow(() -> bookDAO.showAllBorrowedOutOfDate());
    }

    @Test
    public void successfulShowingAllBorrowedBooks() {
        Assertions.assertDoesNotThrow(() -> bookDAO.showAllBorrowedBooks());
    }

    @Test
    public void successfulShowingAllBooks() {
        Assertions.assertDoesNotThrow(() -> bookDAO.showAllBooks());
    }
}
