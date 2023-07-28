package hr.rba.creditcardapplication.services.impl;

import hr.rba.creditcardapplication.models.entities.File;
import hr.rba.creditcardapplication.repositories.FileRepository;
import hr.rba.creditcardapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
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
