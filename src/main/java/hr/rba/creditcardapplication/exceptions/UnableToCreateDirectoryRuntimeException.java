package hr.rba.creditcardapplication.exceptions;

public class UnableToCreateDirectoryRuntimeException extends RuntimeException {
    public static final String ERROR_MSG = "Unable to create a directory: ";
    public UnableToCreateDirectoryRuntimeException(final String directoryName) {
        super(String.format("%s: %s", ERROR_MSG, directoryName));
    }
}
