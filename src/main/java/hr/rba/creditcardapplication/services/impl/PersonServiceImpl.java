package hr.rba.creditcardapplication.services.impl;


import hr.rba.creditcardapplication.PersonFileWriter;
import hr.rba.creditcardapplication.exceptions.PersonNotFoundRuntimeException;
import hr.rba.creditcardapplication.exceptions.PersonOibExistsRuntimeException;
import hr.rba.creditcardapplication.models.dtos.PersonDTO;
import hr.rba.creditcardapplication.models.entities.Person;
import hr.rba.creditcardapplication.repositories.PersonRepository;
import hr.rba.creditcardapplication.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PersonFileWriter personFileWriter;

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person getOneByOib(final String oib) {
        Optional<Person> person = personRepository.findByOib(oib);
        person.ifPresent(personFileWriter::createPersonFile);
        return person.orElseThrow(() -> new PersonNotFoundRuntimeException(oib));
    }
    @Override
    public Person storePerson(final Person person) {
        return this.savePerson(person);
    }

    @Override
    public Person updateExistingPerson(final Person newPersonValue, final Long id) {
        return Optional.of(personRepository.findById(id).orElseThrow(() -> new PersonNotFoundRuntimeException(newPersonValue.getOib())))
                .map(existingPerson -> {
                    existingPerson.setOib(newPersonValue.getOib());
                    existingPerson.setName(newPersonValue.getName());
                    existingPerson.setLastName(newPersonValue.getLastName());
                    existingPerson.setCreditCardStatus(newPersonValue.getCreditCardStatus());
                    return this.savePerson(existingPerson);
                }).orElseThrow(() -> new PersonOibExistsRuntimeException(newPersonValue));
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
        final HttpStatus httpStatus;
        if (!this.personRepository.removePersonByOib(oib).equals("0")) {
            httpStatus = HttpStatus.NO_CONTENT;
        } else {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return httpStatus;
    }

    private Person savePerson(final Person person) {
        return personRepository.save(person);
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
