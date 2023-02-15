package pl.edu.wszib.library.engine;

import pl.edu.wszib.library.database.BookDAO;
import pl.edu.wszib.library.database.UserDAO;
import pl.edu.wszib.library.gui.GUI;

public class Engine {
    final GUI gui = GUI.getInstance();
    final UserDAO userDAO = UserDAO.getInstance();
    final BookDAO bookDAO = BookDAO.getInstance();
    final Authenticator authenticator = Authenticator.getInstance();
    private static final Engine instance = new Engine();

    public void start(){
        boolean isRunning = true;
        boolean isLogged = false;

        while (isRunning){
            switch (gui.showMenu()) {
                case "1":
                    System.out.println("=".repeat(20));
                    System.out.println("Registration process...");
                    userDAO.userAdd(gui.readNewUser());
                    System.out.println("Your account is ready.");
                    break;
                case "2":
                    System.out.println("=".repeat(20));
                    System.out.println("Logging...");
                    this.authenticator.authenticate(this.gui.readLoginAndPassword());
                    isLogged = this.authenticator.getLoggedUser() != null;
                    if (!(isLogged)) {
                        System.out.println("No authorization!");
                    }
                    break;
                case "3":
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
                switch (gui.showUserMenu()) {
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "5":
                        break;
                    case "6":
                        System.out.println("=".repeat(20));
                        System.out.println("Adding new position");
                        bookDAO.addBook(gui.readNewBook());
                        break;
                    case "7":
                        System.out.println("=".repeat(20));
                        System.out.println("Logged out\n");
                        isLogged = false;
                        this.authenticator.getUserLoggedOut();
                        break;
                    default:
                        System.out.println("=".repeat(20));
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
