package filemanager.exceptions;

public class DateIsEmptyException extends RuntimeException {

    public DateIsEmptyException() {
        super();
    }

    public DateIsEmptyException(String message) {
        super(message);
    }

}
