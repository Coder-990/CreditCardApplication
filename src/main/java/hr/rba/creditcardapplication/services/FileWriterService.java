package hr.rba.creditcardapplication.services;

import hr.rba.creditcardapplication.models.entities.Person;

public interface FileWriterService {
    void writeValuesToFile(Person person);

    boolean isFileDeletedOfDeletedPerson(String oib);
}
