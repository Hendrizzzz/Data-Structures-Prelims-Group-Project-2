package prelim;

/**
 * Exception thrown when an operation attempts to add elements to a data structure that has reached its capacity.
 * This exception indicates that the data structure cannot accommodate more elements.
 */
public class ListOverflowException extends Exception {

    /**
     * Constructs a new {@code ListOverflowException} with the default detail message.
     * The default message is "List overflow error occurred".
     */
    public ListOverflowException() {
        super("List overflow error occurred");
    }

    /**
     * Constructs a new {@code ListOverflowException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ListOverflowException(String message) {
        super(message);
    }
}
