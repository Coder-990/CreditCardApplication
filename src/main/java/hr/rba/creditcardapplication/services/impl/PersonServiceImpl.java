package hr.rba.creditcardapplication.services.impl;


import hr.rba.creditcardapplication.PersonFileWriter;
import hr.rba.creditcardapplication.exceptions.PersonNotFoundByOibRuntimeException;
import hr.rba.creditcardapplication.exceptions.PersonNotFoundRuntimeException;
import hr.rba.creditcardapplication.models.dtos.PersonDTO;
import hr.rba.creditcardapplication.models.entities.Person;
import hr.rba.creditcardapplication.repositories.PersonRepository;
import hr.rba.creditcardapplication.services.FileService;
import hr.rba.creditcardapplication.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PersonFileWriter personFileWriter;
    private final FileService fileService;

    @Override
    public List<Person> getAll() {
        return this.personRepository.findAll();
    }

    @Override
    public Person getOneByOib(final String oib) {
        Optional<Person> person = this.personRepository.findByOib(oib);
        person.ifPresent(this.personFileWriter::createPersonFile);
        return person.orElseThrow(() -> new PersonNotFoundByOibRuntimeException(oib));
    }

    public Person getOneByOibInActive(final String oib) {
        Optional<Person> person = this.personRepository.findByOib(oib);
        return person.orElseThrow(() -> new PersonNotFoundByOibRuntimeException(oib));
    }

    @Override
    public Person storePerson(final Person person) {
        return this.saveActivePerson(person);
    }

    @Override
    public Person updateExistingPerson(final Person newPersonValue, final Long id) {
        return Optional.of(this.personRepository.findById(id).orElseThrow(() -> new PersonNotFoundRuntimeException(newPersonValue.getId())))
                .map(existingPerson -> {
                    existingPerson.setOib(newPersonValue.getOib());
                    existingPerson.setName(newPersonValue.getName());
                    existingPerson.setLastName(newPersonValue.getLastName());
                    existingPerson.setFile(newPersonValue.getFile());
                    return this.personRepository.save(existingPerson);
                }).orElseThrow(() -> new PersonNotFoundRuntimeException(newPersonValue.getId()));
    }

    @Override
    public HttpStatus deletePersonById(final Long id) {
        final HttpStatus httpStatus;
        if (this.personRepository.removePersonById(id) > 0) {
            httpStatus = HttpStatus.NO_CONTENT;
        } else {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return httpStatus;
    }

    @Override
    public HttpStatus deletePersonByOib(final String oib) {
        this.saveInActivePerson(this.getOneByOibInActive(oib));
        personFileWriter.deleteFileOfDeletedPerson(oib);
        this.getOneByOib(oib);
        final HttpStatus httpStatus;
        if (!this.personRepository.removePersonByOib(oib).equals("0")) {
            httpStatus = HttpStatus.NO_CONTENT;
        } else {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return httpStatus;
    }

    private void saveInActivePerson(final Person person) {
        person.setFile(this.fileService.storeNewInactiveFile());
        this.personRepository.save(person);
    }
    private Person saveActivePerson(final Person person) {
        person.setFile(this.fileService.storeFile());
        return this.personRepository.save(person);
    }

    @Override
    public ResponseEntity<PersonDTO> getPersonDTOResponseEntity(final Person person) {
        final ResponseEntity<PersonDTO> responseEntity;
        if (person != null) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(this.convertToDto(person));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<PersonDTO> savePersonDTOResponseEntity(final Person person) {
        final ResponseEntity<PersonDTO> responseEntity;
        if (person != null) {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(this.convertToDto(person));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return responseEntity;
    }

    @Override
    public Person convertToEntity(final PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    @Override
    public PersonDTO convertToDto(final Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
