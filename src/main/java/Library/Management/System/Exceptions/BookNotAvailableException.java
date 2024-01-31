package Library.Management.System.Exceptions;

public class BookNotAvailableException extends Exception{
    public BookNotAvailableException(String message) {
        super(message);
    }
}