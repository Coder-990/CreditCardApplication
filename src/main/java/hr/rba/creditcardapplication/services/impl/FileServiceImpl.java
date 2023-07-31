package hr.rba.creditcardapplication.services.impl;

import hr.rba.creditcardapplication.models.entities.File;
import hr.rba.creditcardapplication.repositories.FileRepository;
import hr.rba.creditcardapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hr.rba.creditcardapplication.util.Constants.ACTIVE;
import static hr.rba.creditcardapplication.util.Constants.INACTIVE;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


    private final FileRepository fileRepository;

    @Override
    public File storeFile() {
        final File file = File.builder().status(ACTIVE).build();
        return this.fileRepository.save(file);
    }

    @Override
    public File storeNewInactiveFile() {
        final File file = File.builder().status(INACTIVE).build();
        return this.fileRepository.save(file);
    }
}
