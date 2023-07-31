package hr.rba.creditcardapplication.exceptions;

public class FileAlreadyExistsRuntimeException extends RuntimeException {
    public static final String ERROR_MSG = "File by this oib exists: ";
    public FileAlreadyExistsRuntimeException(final String oib) {
        super(String.format("%s: %s", ERROR_MSG, oib));
    }
}
