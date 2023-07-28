package hr.rba.creditcardapplication.exceptions;

public class PersonNotFoundRuntimeException extends RuntimeException {
    public static final String ERROR_MSG = "Could not find person by this id: ";
    public PersonNotFoundRuntimeException(final Long id) {
        super(String.format("%s: %d", ERROR_MSG, id));
    }
}
