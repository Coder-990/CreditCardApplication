package hr.rba.creditcardapplication.services;

import hr.rba.creditcardapplication.models.entities.File;

public interface FileService {

    File storeFile();

    File storeNewInactiveFile();
}
