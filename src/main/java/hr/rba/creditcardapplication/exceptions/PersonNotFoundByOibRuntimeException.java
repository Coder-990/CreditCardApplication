package hr.rba.creditcardapplication.exceptions;

public class PersonNotFoundByOibRuntimeException extends RuntimeException{

    public static final String ERROR_MSG = "Could not find person by this oib: ";
    public PersonNotFoundByOibRuntimeException(final String oib) {
        super(String.format("%s: %s", ERROR_MSG, oib));
    }
}
