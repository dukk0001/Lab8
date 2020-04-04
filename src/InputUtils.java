/*
 * Author: Svetlana Dukkardt
 * Student number: 040851905
 * Lab: 8
 * Date: 4 April, 2020
 * Purpose: Provides the set of methods that helps to read and validate user input
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Provides the set of methods that helps to read and validate user input from {@link System#in input stream}
 *
 * @author Svetlana Dukkardt
 */
public class InputUtils {
    /**
     * Pattern that is used to parse / print {@link LocalDate date} value
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Predicate that validates the users inputs for main menu choices
     */
    public static final Predicate<Integer> MAIN_MENU_CHOICES = value -> value > 0 && value <= 3;
    
    /**
     * Predicate that validates the users inputs for integers greater than zero
     */
    public static final Predicate<Integer> INTEGER_GREATER_THAN_ZERO = value -> value > 0;

    /**
     * Predicate that validates the users inputs for floats greater than zero
     */
    public static final Predicate<Float> FLOAT_GREATER_THAN_ZERO = value -> value > 0;

    /**
     * Default printer to print prompt message to the user
     */
    private static final Consumer<String> DEFAULT_PRINTER = System.out::print;
    
    /**
     * Functions that is used to parse data from user input.
     */
    public static final Function<String, LocalDate> dateParser = value -> {
        if ("none".equals(value)) {
            return LocalDate.MAX;
        }
        //convert String to LocalDate
        return LocalDate.parse(value, DATE_FORMATTER);
    };
    
    /**
     * Constructor.
     */
    private InputUtils() {}
    
    /**
     * Reads and validates the {@link String value} from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getString(Scanner, String, String)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @return {@link String value} that user entered.
     */
    public static String getString(Scanner scanner, String promptMessage) {
        return getString(scanner, promptMessage, "");
    }
    
    /**
     * Reads and validates the {@link String value} from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getString(Scanner, Predicate, String, String)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @return {@link String value} that user entered.
     */
    public static String getString(Scanner scanner, Predicate<String> predicate, String promptMessage) {
        return getString(scanner, predicate, promptMessage, "");
    }
    
    /**
     * Reads and validates the {@link String value} from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getString(Scanner, Predicate, String, String)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @return {@link String value} that user entered.
     */
    public static String getString(Scanner scanner, String promptMessage, String alert) {
        return getString(scanner, Objects::nonNull, promptMessage, alert);
    }
    
    /**
     * Reads and validates the {@link String value} from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     *
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @return {@link String value} that user entered.
     */
    public static String getString(Scanner scanner, Predicate<String> predicate, String promptMessage, String alert) {
        return consoleReader(scanner, value -> value, predicate, promptMessage, DEFAULT_PRINTER, alert);
    }
    
    /**
     * Reads and validates the {@link LocalDate date value} from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getDate(Scanner, String, Consumer, String, boolean)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @return {@link LocalDate date value} that user entered.
     */
    public static LocalDate getDate(Scanner scanner, String promptMessage, String alert) {
        return getDate(scanner, promptMessage, DEFAULT_PRINTER, alert, false);
    }
    
    /**
     * Reads and validates the {@link LocalDate date value} from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     *
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param promptPrinter   - {@link Consumer prompt message printer} that is prints message to user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @param fromFile - indicated that data is read from file
     * @return {@link LocalDate date value} that user entered.
     */
    public static LocalDate getDate(Scanner scanner, String promptMessage,
                                    Consumer<String> promptPrinter, String alert, boolean fromFile){
        if (fromFile) {
            return readValue(scanner, dateParser, Objects::nonNull, promptMessage, promptPrinter, alert);
        } else {
            return consoleReader(scanner, dateParser, Objects::nonNull, promptMessage, promptPrinter, alert);
        }
    }
    
    /**
     * Reads and validates the float value from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getFloat(Scanner, Predicate, String, Consumer, String)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @return float value that user entered.
     */
    public static float getFloat(Scanner scanner, Predicate<Float> predicate, String promptMessage, String alert){
       return getFloat(scanner, predicate, promptMessage, DEFAULT_PRINTER, alert);
    }
    
    /**
     * Reads and validates the float value from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getFloat(Scanner, Predicate, String, Consumer, String, boolean)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param promptPrinter   - {@link Consumer prompt message printer} that is prints message to user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @return float value that user entered.
     */
    public static float getFloat(Scanner scanner, Predicate<Float> predicate, String promptMessage,
                                 Consumer<String> promptPrinter, String alert){
        return getFloat(scanner, predicate, promptMessage, promptPrinter, alert, false);
    }
    
    /**
     * Reads and validates the float value from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     *
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param promptPrinter   - {@link Consumer prompt message printer} that is prints message to user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @param fromFile - indicated that data is read from file
     * @return float value that user entered.
     */
    public static float getFloat(Scanner scanner, Predicate<Float> predicate, String promptMessage,
                                 Consumer<String> promptPrinter, String alert, boolean fromFile){
        if (fromFile) {
            return readValue(scanner, Float::parseFloat, predicate, promptMessage, promptPrinter, alert);
        } else {
            return consoleReader(scanner, Float::parseFloat, predicate, promptMessage, promptPrinter, alert);
        }
    }

    
    
    /**
     * Reads and validates the integer value from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input.
     * Overloads {@link InputUtils#getInt(Scanner, Predicate, String, String)} method
     * @param scanner - {@link Scanner scanner} instance that reads user inputs
     * @param promptMessage - {@link String prompt message} that is shown for user
     * @param alert   - {@link String error message} that is shown to user in case of invalid input
     * @return int value that user entered.
     */
    public static int getInt(Scanner scanner, String promptMessage, String alert) {
        return getInt(scanner, null, promptMessage, alert);
    }

    /**
     * Reads and validates the integer value from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getInt(Scanner, Predicate, String, Consumer, String)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @return int value that user entered.
     */
    public static int getInt(Scanner scanner, Predicate<Integer> predicate, String promptMessage, String alert) {
        return getInt(scanner, predicate, promptMessage, DEFAULT_PRINTER, alert);
    }

    /**
     * Reads and validates the integer value from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     * Overloads {@link InputUtils#getInt(Scanner, Predicate, String, Consumer, String, boolean)} method
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param promptPrinter   - {@link Consumer prompt message printer} that is prints message to user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @return int value that user entered.
     */
    public static int getInt(Scanner scanner, Predicate<Integer> predicate, String promptMessage,
                             Consumer<String> promptPrinter, String alert) {
        return getInt(scanner, predicate, promptMessage, promptPrinter,alert, false);
    }
    
    /**
     * Reads and validates the integer value from user input.
     * In case of user enters invalid value the method prints the error message provided by alert parameter
     * and requests the new attempt to input
     *
     * @param scanner   - {@link Scanner scanner} instance that reads user inputs
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param promptPrinter   - {@link Consumer prompt message printer} that is prints message to user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @param fromFile - indicated that data is read from file
     * @return int value that user entered.
     */
    public static int getInt(Scanner scanner, Predicate<Integer> predicate, String promptMessage,
                             Consumer<String> promptPrinter, String alert, boolean fromFile) {
        if (fromFile) {
            return readValue(scanner, Integer::parseInt, predicate, promptMessage, promptPrinter, alert);
        } else {
            return consoleReader(scanner, Integer::parseInt, predicate, promptMessage, promptPrinter, alert);
        }
    }

    /**
     * Validates the provided value from user input.
     *
     * @param value     - value to validate
     * @param predicate - {@link Predicate expression} that is used to perform custom validation for the provided value, if null - executes only null check.
     * @param message   - {@link String error message} in case of invalid input detected
     * @param <T>       - the type of value.
     * @return - true - if validation succeed, otherwise - false
     */
    private static <T> boolean validateValue(T value, Predicate<T> predicate, String message) {
        boolean result = value != null;
        result = (result && predicate != null) ? predicate.test(value) : result;
        return result;
    }

    /**
     * Prints the message to the user.
     * Overloads {@link InputUtils#printPromptMessage(String, Consumer)} method
     * and provides {@link InputUtils#DEFAULT_PRINTER} as printer
     * @param message message to print
     */
    private static void  printPromptMessage(String message) {
        printPromptMessage(message, DEFAULT_PRINTER);
    }

    /**
     * Prints the message to the user
     * @param message message to print
     * @param promptPrinter printer that is used to print message. if null then uses the {@link InputUtils#DEFAULT_PRINTER}
     */
    private static void  printPromptMessage(String message, Consumer<String> promptPrinter) {
        if (promptPrinter != null) {
            promptPrinter.accept(message);
        } else {
            DEFAULT_PRINTER.accept(message);
        }
    }
    
    /**
     * Reads data value from the standard input stream and validates it
     * @param scanner {@link Scanner scanner} instance that reads user inputs
     * @param parser - {@link Function expression} that is used to perform conversion from String to expected data type.
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param promptPrinter   - {@link Consumer prompt message printer} that is prints message to user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @param <T> - exact type of returning data that is expected
     * @return valid data that is read from user input.
     */
    private static <T> T consoleReader(Scanner scanner, Function<String, T> parser, Predicate<T> predicate, String promptMessage,
                                       Consumer<String> promptPrinter, String alert) {
        T value = null;
        do {
            try {
                value = readValue(scanner,  parser, predicate, promptMessage, promptPrinter, alert);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (value==null);
        return value;
    }
    
    /**
     * Reads data value from the user input and validates it
     * @param scanner {@link Scanner scanner} instance that reads user inputs
     * @param parser - {@link Function expression} that is used to perform conversion from String to expected data type.
     * @param predicate - {@link Predicate expression} that is used to perform custom validation on the user input, if null - executes only null check.
     * @param promptMessage   - {@link String prompt message} that is shown for user
     * @param promptPrinter   - {@link Consumer prompt message printer} that is prints message to user
     * @param alert     - {@link String error message} that is shown to user in case of invalid input
     * @param <T> - exact type of returning data that is expected
     * @return valid data that is read from user input.
     * @throws RuntimeException in case of data cannot be read or invalid input detected.
     */
    private static <T> T readValue(Scanner scanner, Function<String, T> parser, Predicate<T> predicate, String promptMessage,
                                   Consumer<String> promptPrinter, String alert) {
        printPromptMessage(promptMessage, promptPrinter);
        try {
            T value = parser.apply(scanner.nextLine());
            if (!validateValue(value, predicate, alert)) {
                throw new RuntimeException(alert);
            }
            return value;
        } catch (Exception e) {
            String msg = alert;
            if (e instanceof DateTimeParseException) {
                msg += System.lineSeparator() + e.getMessage();
            }
            throw new RuntimeException(msg);
        }
    }
}
