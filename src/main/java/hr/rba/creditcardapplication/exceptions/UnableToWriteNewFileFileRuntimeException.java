package hr.rba.creditcardapplication.exceptions;

import hr.rba.creditcardapplication.models.entities.Person;

public class UnableToWriteNewFileFileRuntimeException extends RuntimeException {
        public static final String ERROR_MSG = "Unable to write a file to disk: ";
        public UnableToWriteNewFileFileRuntimeException(final Person person) {
            super(String.format("%s: %s", ERROR_MSG, person.toString()));
        }
}
