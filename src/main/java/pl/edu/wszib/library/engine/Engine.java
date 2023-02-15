package pl.edu.wszib.library.engine;

import pl.edu.wszib.library.database.UserDAO;
import pl.edu.wszib.library.gui.GUI;

public class Engine {
    final GUI gui = GUI.getInstance();
    private static final Engine instance = new Engine();
    private final UserDAO userDAO = UserDAO.getInstance();

    public void start(){
        boolean isRunning = true;

        while (isRunning){
            switch (gui.showMenu()) {
                case "1":
                    System.out.println("Registration process...");
                    userDAO.userAdd(gui.readNewUser());
                    System.out.println("Your account is ready.");
                    break;
                case "2":
                    System.out.println("Welcome again!");
                    break;
                case "3":
                    isRunning = false;
                    System.out.println("Exit from application");
                    break;
                default:
                    System.out.println("Something is wrong. Please try again!");
                    break;
            }
        }
    }

    public static Engine getInstance() {
        return instance;
    }
}
