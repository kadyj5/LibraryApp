package pl.edu.wszib.library.database;

import org.junit.jupiter.api.*;
import pl.edu.wszib.library.entity.User;

import java.sql.Connection;
import java.util.Optional;

public class UserDAOtest {

    UserDAO userDAO = UserDAO.getInstance();


    @BeforeEach
    public void prepare() {
        System.out.println("Preparing for the test !!!");

    }
    @AfterEach
    public void clean() {
        System.out.println("Cleaning after the test");
    }
    @BeforeAll
    public static void prepareAll() {
        System.out.println("Preparing for tests");
    }
    @AfterAll
    public static void cleanAfterAll() {
        System.out.println("Cleaning after tests");
    }


    @Test
    public void SuccessedFindByLogin() {
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

        Assertions.assertAll(
                "Assertions of 'admin' user",
                () -> Assertions.assertEquals(expectedResult.getId(), actual.get().getId()),
                () -> Assertions.assertEquals(expectedResult.getLogin(),actual.get().getLogin()),
                () -> Assertions.assertEquals(expectedResult.getPassword(),actual.get().getPassword()),
                () -> Assertions.assertEquals(expectedResult.getRole(),actual.get().getRole()),
                () -> Assertions.assertEquals(expectedResult.getName(),actual.get().getName()),
                () -> Assertions.assertEquals(expectedResult.getSurname(),actual.get().getSurname()));
//        Assertions.assertTrue(
//                (expectedResult.getId() == actual.get().getId()) &&
//                        (expectedResult.getLogin().equals(actual.get().getLogin())) &&
//                        (expectedResult.getPassword().equals(actual.get().getPassword())) &&
//                        (expectedResult.getRole()).equals(actual.get().getRole()) &&
//                        (expectedResult.getName().equals(actual.get().getName())) &&
//                        (expectedResult.getSurname().equals(actual.get().getSurname())));

    }

}
