package hr.rba.creditcardapplication.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.rba.creditcardapplication.exceptions.FileAlreadyExistsRuntimeException;
import hr.rba.creditcardapplication.exceptions.PersonNotFoundByOibRuntimeException;
import hr.rba.creditcardapplication.exceptions.UnableToWriteNewFileFileRuntimeException;
import hr.rba.creditcardapplication.models.entities.Person;
import hr.rba.creditcardapplication.services.FileWriterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static hr.rba.creditcardapplication.util.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileWriterServiceImpl implements FileWriterService {

    private final ObjectMapper objectMapper;

    @Override
    public void createPersonFile(final Person person) {
        final File file = new File(FILE_LOCATION, person.getOib() + " " + Timestamp.from(Instant.now()).toString().replace(":", "-"));
        if (!this.checkIfFileExists(file.getName().substring(0, 11))) {
            try (FileOutputStream fos = new FileOutputStream(file);
                 final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
                bw.write(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person));
//                bw.write(this.buildPersonValuesInNewLineAsPlainString(person).toString());
                log.info(SUCESS_MSG_FILE_WRITTEN);
            } catch (RuntimeException | IOException ex) {
                log.error(ERROR_MSG_FILE_WRITING + new UnableToWriteNewFileFileRuntimeException(person), ex.fillInStackTrace());
            }
        }
    }

    @Override
    public boolean isFileDeletedOfDeletedPerson(final String oib) {
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        Arrays.stream(Objects.requireNonNull(new File(FILE_LOCATION).listFiles())).toList()
                .forEach(file -> {
                    if (file.getName().substring(0, 11).equals(oib)) {
                        try {
                            Files.delete(file.toPath());
                            isDeleted.set(true);
                            log.info(OLD_FILE_IS_DELETED);
                        } catch (IOException ex) {
                            isDeleted.set(false);
                            log.error(DELETING_NON_EXISTING_PERSON + new PersonNotFoundByOibRuntimeException(oib), ex.fillInStackTrace());
                        }
                    }
                });
        return isDeleted.get();
    }

    private boolean checkIfFileExists(final String oib) {
        AtomicBoolean isFileEquals = new AtomicBoolean(false);
        Arrays.stream(Objects.requireNonNull(new File(FILE_LOCATION).listFiles())).toList()
                .forEach(file -> {
                    if (file.getName().substring(0, 11).equals(oib)) {
                        isFileEquals.set(true);
                        log.error(ERROR_MSG_FILE_EXISTS, new FileAlreadyExistsRuntimeException(oib));
                    }
                });
        return isFileEquals.get();
    }

    private StringBuilder buildPersonValuesInNewLineAsPlainString(final Person person) throws JsonProcessingException {
        return new StringBuilder()
                .append(objectMapper.writeValueAsString(person.getOib())).append("\n")
                .append(objectMapper.writeValueAsString(person.getName())).append("\n")
                .append(objectMapper.writeValueAsString(person.getLastName())).append("\n")
                .append(objectMapper.writeValueAsString(person.getFile().getStatus()));
    }
}
