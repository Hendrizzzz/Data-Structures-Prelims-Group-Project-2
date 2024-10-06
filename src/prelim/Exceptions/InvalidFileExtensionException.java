package prelim.Exceptions;

public class InvalidFileExtensionException extends RuntimeException{

    public InvalidFileExtensionException() {
        super();
    }

    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
