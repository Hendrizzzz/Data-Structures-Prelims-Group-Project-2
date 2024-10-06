package prelim.Exceptions;

public class InvalidFileEntityNameException extends RuntimeException{

    public InvalidFileEntityNameException() {
        super();
    }


    public InvalidFileEntityNameException(String message) {
        super(message);
    }
}
