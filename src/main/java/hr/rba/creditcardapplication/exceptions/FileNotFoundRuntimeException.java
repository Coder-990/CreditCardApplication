package hr.rba.creditcardapplication.exceptions;

public class FileNotFoundRuntimeException extends RuntimeException {

    public static final String ERROR_MSG = "Could not find file by this id: ";

    public FileNotFoundRuntimeException(final Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
