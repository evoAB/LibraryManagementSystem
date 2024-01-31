package Library.Management.System.Exceptions;

public class MaxLimitReachedException extends Exception{

    public MaxLimitReachedException(String message) {
        super(message);
    }
}