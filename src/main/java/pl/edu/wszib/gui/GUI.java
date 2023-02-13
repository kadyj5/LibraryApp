package pl.edu.wszib.gui;

import java.util.Scanner;

public class GUI {
    private static final GUI instance = new GUI();
    private final Scanner scanner = new Scanner(System.in);

    public String showMenu() {
        System.out.println("""
                1. Sign in
                2. Log in
                3. Exit""");
        return this.scanner.nextLine().trim();
    }


    public static GUI getInstance(){
        return instance;
    }
}
