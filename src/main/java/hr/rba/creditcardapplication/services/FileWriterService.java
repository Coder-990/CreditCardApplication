package hr.rba.creditcardapplication.services;

import hr.rba.creditcardapplication.models.entities.Person;

public interface FileWriterService {
    void createPersonFile(Person person);

    boolean isFileDeletedOfDeletedPerson(String oib);
}
