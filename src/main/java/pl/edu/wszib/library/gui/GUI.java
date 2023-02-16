package pl.edu.wszib.library.gui;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.library.database.UserDAO;
import pl.edu.wszib.library.engine.Authenticator;
import pl.edu.wszib.library.entity.Book;
import pl.edu.wszib.library.entity.User;

import java.util.Scanner;

public class GUI {
    
    private static final GUI instance = new GUI();
    private final Scanner scanner = new Scanner(System.in);
    private final UserDAO userDAO = UserDAO.getInstance();
    private final Authenticator authenticator = Authenticator.getInstance();

    public String showMenu() {
        System.out.println("""
                1. Sign in
                2. Log in
                3. Exit""");
        return this.scanner.nextLine().trim();
    }

    public String showUserMenu() {
        System.out.println("""
                1. Find book
                2. Borrow book
                3. Show all books
                4. Show borrowed books
                5. Show borrowed books with the exceeded date of return
                6. Add book
                7. Log out
                """);
        return this.scanner.nextLine().trim();
    }

    public Book readNewBook() {
        Book book = new Book();
        System.out.println("New Title:");
        book.setTitle(scanner.nextLine().trim());
        System.out.println("Author:");
        book.setAuthor(scanner.nextLine().trim());
        System.out.println("ISBN:");
        book.setIsbn(scanner.nextLine().trim());
        return book;
    }

    public String getInfoOfBook(){
        System.out.print("Search: ");
        return scanner.nextLine().trim();
    }

    public void borrowBook(boolean available) {
        if (available) {
            System.out.println("Enjoy reading!");
        } else {
            System.out.println("Sorry this book is borrowed by someone else");
        }
    }

    public User readLoginAndPassword() {
        User user = new User();
        user.setLogin(readLogin());
        user.setPassword(readPassword());
        return user;
    }

    public int readId() {
        System.out.print("ID: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String readLogin() {
        System.out.print("Login: ");
        return this.scanner.nextLine().trim();
    }

    public String readPassword() {
        System.out.print("Password: ");
        return this.scanner.nextLine().trim();
    }

    public String readName() {
        System.out.println("What is your name?");
        return this.scanner.nextLine().trim();
    }

    public String readSurname() {
        System.out.println("What is your surname?");
        return this.scanner.nextLine().trim();
    }

    public User readNewUser() {
        String login;
        do {
            login = readLogin();
            if (userDAO.findByLogin(login) != null) {
                System.out.println("This login is already used");
            }
        } while (userDAO.findByLogin(login) != null);
        System.out.println("This login has not been used");
        String password = DigestUtils.md5Hex(readPassword() + authenticator.getSeed());
        String name = readName();
        String surname = readSurname();
        return new User(login, password, User.Role.USER, name,surname);
    }

    public static GUI getInstance(){
        return instance;
    }
}
