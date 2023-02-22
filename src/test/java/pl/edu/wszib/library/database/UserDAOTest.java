package pl.edu.wszib.library.database;

import org.junit.jupiter.api.*;
import pl.edu.wszib.library.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDAOTest {

    UserDAO userDAO = UserDAO.getInstance();

    @Test
    public void successfulFindByLogin() {
        String login = "admin";
        User expectedResult = new User(
                1,
                "admin",
                "336284edfa1cdc721ca975e60ae6e2b0",
                User.Role.ADMIN,
                "Karolina",
                "Dyjak"
        );
        Optional<User> actual = userDAO.findByLogin(login);
        actual.ifPresent(user -> Assertions.assertAll(
                "Assertions of 'admin' user",
                () -> Assertions.assertEquals(expectedResult.getId(), user.getId()),
                () -> Assertions.assertEquals(expectedResult.getLogin(), user.getLogin()),
                () -> Assertions.assertEquals(expectedResult.getPassword(), user.getPassword()),
                () -> Assertions.assertEquals(expectedResult.getRole(), user.getRole()),
                () -> Assertions.assertEquals(expectedResult.getName(), user.getName()),
                () -> Assertions.assertEquals(expectedResult.getSurname(), user.getSurname())));
    }

    @Test
    public void failedChangeRoleAdminToAdmin() {
        String login = "admin";
        Assertions.assertFalse(
                userDAO.changeRole(login),
                "Role CHANGED!!!!!");
    }

    @Test
    public void failedUserAdd() {
        User testUser = new User();
        Exception exception = assertThrows(NullPointerException.class, () -> userDAO.userAdd(testUser));
        String expectedMessage = "is null";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
