package pl.edu.wszib.engine;

import pl.edu.wszib.gui.GUI;

public class Engine {
    final GUI gui = GUI.getInstance();
    private static final Engine instance = new Engine();

    public void start(){
        boolean isRunning = true;

        while (isRunning){
            switch (gui.showMenu()) {
                case "1":
                    System.out.println("Registration process...");
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
