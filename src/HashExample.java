/*
 * Author: Svetlana Dukkardt
 * Student number: 040851905
 * Lab: 8
 * Date: 4 April, 2020
 * Purpose: Provides methods to store and search data base on hash algorithm
 */


import java.util.Scanner;

/**
 * Provides methods to store and search data base on hash algorithm
 * @author Svetlana Dukkardt
 */
public class HashExample {
    /**
     * Array to store the data
     */
    private String[] dataItems;
    /**
     * Maximum number of elements to store
     */
    private static final int MAX_SIZE = 100;
    
    /**
     * Constructor
     */
    public HashExample() {
        this.dataItems = new String[]{};
    }
    
    /**
     * Requests and adds a new String to the array
     * @param scanner - scanner that reads user data
     */
    public void addString(Scanner scanner) {
        String userInput = InputUtils.getString(scanner, "Enter the string value to add: ");
        // get the index where to insert the new String
        int index = getIndex(userInput);
        if (MAX_SIZE <= index) {
            System.out.println("String cannot be added");
            return;
        }
        addToArray(index, userInput);
    }
    
    /**
     * Search for a string with in the array.
     * @param scanner scanner that reads user data
     */
    public void searchString(Scanner scanner) {
        String userInput = InputUtils.getString(scanner, "Enter the string value to search: ");
        // get the start index where entered String can be found
        int index = getIndex(userInput);
        if (MAX_SIZE > index) {
            // if the index less than 100 then we need to try to find the string
            for (int i = index; i < dataItems.length && dataItems[i]!=null; i++) {
                if (userInput.equals(dataItems[i])) {
                    System.out.println("String Index: " + i);
                    return;
                }
            }
        }
        System.out.println("String is not found");
    }
    
    /**
     * Adds a value to the array if there is a room.
     * @param index - where to add
     * @param value - value to add
     */
    private void addToArray(int index, String value) {
        expandIfRequired(index);
        // there is a value in this index. need to find next available index
        while (index < dataItems.length && dataItems[index]!=null) {
            index ++;
        }
        // check if we are with in max size
        if (MAX_SIZE <= index) {
            System.out.println("String cannot be added");
            return;
        }
        expandIfRequired(index);
        
        dataItems[index] = value;
    }
    
    /**
     * Expand the array capacity if it is required.
     * @param index - where we need to add
     */
    private void expandIfRequired(int index) {
        if (index >= dataItems.length) {
            // index is out of boundary of the array. need to extend it.
            String[] tmp = new String[index + 1];
            System.arraycopy(dataItems,0, tmp,0, dataItems.length);
            dataItems = tmp;
        }
    }
    
    /**
     * Retrieves the index where to insert the value
     * @param value value to insert
     * @return - index where to insert
     */
    private int getIndex(String value) {
        return getHash(value);
    }
    
    /**
     * Calculates the hash of the value
     * @param value - value user entered
     * @return hash value
     */
    private int getHash(String value) {
        int firstChar = value.charAt(0);
        int secondChar = 0;
        if (value.length() > 1) {
            secondChar = value.charAt(1);
        }
        return (firstChar + secondChar) % 100;
    }
}
