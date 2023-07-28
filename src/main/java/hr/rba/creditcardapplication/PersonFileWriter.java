package hr.rba.creditcardapplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.rba.creditcardapplication.models.entities.Person;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
public class PersonFileWriter implements Serializable {

    public static final String FILE_LOCATION = "file/";
    public static final String ERROR_MSG_FILE_WRITING = "Error while try to write a file: ";
    public static final String ERROR_MSG_FILE_EXISTS = "Error, file already exists";
    public static final String SUCESS_MSG_FILE_WRITTEN = "Person was successfully found and written to a file";
    public static final String ERROR_MULTI_FILE = "One person can have only one active file";

    public void createPersonFile(final Person person) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final File file = new File(FILE_LOCATION, person.getOib() + " " + Timestamp.from(Instant.now()));
        if (!file.exists()) {
            try (FileOutputStream fos = new FileOutputStream(file);
                 final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
                bw.write(objectMapper.writeValueAsString(person));
//                bw.write(this.buildPersonValuesAsStringInRow(person, objectMapper).toString());
                log.info(SUCESS_MSG_FILE_WRITTEN);
            } catch (RuntimeException | IOException ex) {
                log.error(ERROR_MSG_FILE_WRITING + person, ex.fillInStackTrace());
            }
        } else {
            log.info(ERROR_MSG_FILE_EXISTS);
        }
    }



    private StringBuilder buildPersonValuesAsStringInRow(Person person, ObjectMapper objectMapper) throws JsonProcessingException {
        return new StringBuilder()
                .append(objectMapper.writeValueAsString(person.getOib())).append("\n")
                .append(objectMapper.writeValueAsString(person.getName())).append("\n")
                .append(objectMapper.writeValueAsString(person.getLastName())).append("\n")
                .append(objectMapper.writeValueAsString(person.getFile().getStatus()));

    }
}
