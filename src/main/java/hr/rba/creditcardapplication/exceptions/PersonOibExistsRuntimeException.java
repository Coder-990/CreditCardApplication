package hr.rba.creditcardapplication.exceptions;

import hr.rba.creditcardapplication.models.entities.Person;

public class PersonOibExistsRuntimeException extends RuntimeException {
    public static final String ERROR_MSG = "Person by this oib exists: ";
    public PersonOibExistsRuntimeException(final Person person) {
        super(String.format("%s: %s", ERROR_MSG, person.getOib()));
    }
}
