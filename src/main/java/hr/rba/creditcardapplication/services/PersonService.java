package hr.rba.creditcardapplication.services;


import hr.rba.creditcardapplication.models.dtos.PersonDTO;
import hr.rba.creditcardapplication.models.entities.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {
    List<Person> getAll();

    Person getOneByOib(String oib);

    Person storePerson(Person person);

    Person updateExistingPerson(Person newPersonValue, Long id);

    HttpStatus deletePersonByOib(String oib);

    ResponseEntity<PersonDTO> getPersonDTOResponseEntity(Person person);

    ResponseEntity<PersonDTO> savePersonDTOResponseEntity(Person person);

    Person convertToEntity(PersonDTO personDTO);

    PersonDTO convertToDto(Person person);
}
