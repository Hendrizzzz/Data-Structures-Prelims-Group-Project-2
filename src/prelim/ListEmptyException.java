package prelim;
/**
 * Exception thrown when a function attempts to search for a container within a list that is empty.
 * */
public class ListEmptyException extends Exception {
    /**
     * Constructs a new {@code ListEmptyException} with a specified message.
     *
     * @param message The specified message.
     * */
    public ListEmptyException(String message) {
        super(message);
    }
}