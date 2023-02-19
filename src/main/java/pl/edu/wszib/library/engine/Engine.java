package pl.edu.wszib.library.engine;

import pl.edu.wszib.library.database.BookDAO;
import pl.edu.wszib.library.database.UserDAO;
import pl.edu.wszib.library.entity.User;
import pl.edu.wszib.library.gui.GUI;

public class Engine {

    final GUI gui = GUI.getInstance();
    final UserDAO userDAO = UserDAO.getInstance();
    final BookDAO bookDAO = BookDAO.getInstance();
    final Authenticator authenticator = Authenticator.getInstance();
    private static final Engine instance = new Engine();
    private final String displayDivider = "=".repeat(25);

    public void start(){
        boolean isRunning = true;
        boolean isLogged = false;

        while (isRunning){
            switch (gui.showMenu()) {
                case "1":
                    System.out.println(displayDivider);
                    userDAO.userAdd(gui.readNewUser());
                    System.out.println("Your account is ready.");
                    break;

                case "2":
                    System.out.println(displayDivider);
                    System.out.println("Logging...");
                    this.authenticator.authenticate(this.gui.readLoginAndPassword());
                    isLogged = this.authenticator.getLoggedUser() != null;
                    if (!(isLogged)) {
                        System.out.println("No authorization!");
                    }
                    break;

                case "3":
                    System.out.println(displayDivider);
                    System.out.println("=".repeat(20));
                    isRunning = false;
                    System.out.println("Exit from application");
                    break;

                default:
                    System.out.println("=".repeat(20));
                    System.out.println("Something is wrong. Please try again!");
                    break;

            }
            while (isLogged) {
                switch (gui.showUserMenu(authenticator.getLoggedUser())) {
                    case "1":
                        System.out.println(displayDivider);
                        bookDAO.findSpecificBook(gui.getInfoOfBook());
                        break;

                    case "2":
                        System.out.println(displayDivider);
                        gui.borrowBook(bookDAO.borrowBookById(gui.readId(), authenticator.getLoggedUser()));
                        break;

                    case "3":
                        System.out.println(displayDivider);
                        bookDAO.showAllBooks();
                        break;

                    case "4":
                        System.out.println(displayDivider);
                        System.out.println("Logged out\n");
                        isLogged = false;
                        this.authenticator.getUserLoggedOut();
                        break;

                    case "5":
                        System.out.println(displayDivider);
                        if(this.authenticator.getLoggedUser() != null &&
                                this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                            bookDAO.showAllBorrowedBooks();
                        } else { System.out.println("Permission denied"); }
                        break;

                    case "6":
                        System.out.println(displayDivider);
                        if(this.authenticator.getLoggedUser() != null &&
                                this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                            bookDAO.showAllBorrowedOutOfDate();
                        } else { System.out.println("Permission denied"); }
                        break;

                    case "7":
                        System.out.println(displayDivider);
                        if(this.authenticator.getLoggedUser() != null &&
                                this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                            System.out.println("Adding new position");
                            bookDAO.addBook(gui.readNewBook());
                        } else { System.out.println("Permission denied"); }
                        break;

                    case "8":
                        System.out.println(displayDivider);
                        if(this.authenticator.getLoggedUser() != null &&
                                this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                            gui.showRoleChangeResult(userDAO.changeRole(gui.readLogin()));
                        } else { System.out.println("Permission denied"); }
                        break;

                    case "9":
                        System.out.println(displayDivider);
                        if(this.authenticator.getLoggedUser() != null &&
                                this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                            userDAO.userAdd(gui.readNewUser());
                        } else { System.out.println("Permission denied"); }
                        break;

                    case "10":
                        System.out.println(displayDivider);
                        if (this.authenticator.getLoggedUser() != null &&
                                this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                            userDAO.listUsers();
                    } else { System.out.println("Permission denied"); }
                        break;

                    default:
                        System.out.println(displayDivider);
                        System.out.println("Wrong choice!");
                        break;
                }
            }
        }
    }

    public static Engine getInstance() {
        return instance;
    }
}
