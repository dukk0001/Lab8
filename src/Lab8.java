/*
 * Author: Svetlana Dukkardt
 * Student number: 040851905
 * Lab: 8
 * Date: 4 April, 2020
 * Purpose: Main class for Lab8
 */

import java.util.Scanner;

/**
 * Main class for Lab8
 */
public class Lab8 {
    
    /**
     * {@link Scanner scaner} object that is used to read user input
     */
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int choice;
        HashExample hashExample = new HashExample();
        do {
            choice = InputUtils.getInt(scanner, InputUtils.MAIN_MENU_CHOICES, null, s -> displayMenu(), "Invalid choice");
            switch (choice) {
                case 1:
                    // add a string
                    hashExample.addString(scanner);
                    break;
                case 2:
                    // search for String
                    hashExample.searchString(scanner);
                    break;
            }
        } while (choice != 3);
        System.out.println("Exiting...");
    }
    
    /**
     * Displays  all menu options
     */
    public static void displayMenu() {
        
        String menuBuilder = "Please select one of the following:" + System.lineSeparator() +
                "1: Add a String" + System.lineSeparator() +
                "2: Search for a String" + System.lineSeparator() +
                "3: To Exit" + System.lineSeparator() +
                ">";
        System.out.print(menuBuilder);
    }
}
