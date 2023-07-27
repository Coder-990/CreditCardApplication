package hr.rba.creditcardapplication.controllers;

import hr.rba.creditcardapplication.models.dtos.PersonDTO;
import hr.rba.creditcardapplication.models.entities.Person;
import hr.rba.creditcardapplication.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(PersonController.BASE_URL)
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PersonController {

    public static final String BASE_URL = "api/v1/persons";

    private final PersonService personService;

    @GetMapping()
    public List<PersonDTO> getAll() {
        final List<PersonDTO> personDTOS = this.personService.getAll()
                .stream().map(this.personService::convertToDto).collect(Collectors.toList());
        log.info("Persons initialized successfully");
        return personDTOS;
    }

    @GetMapping("/{oib}")
    public ResponseEntity<PersonDTO> getByOib(@PathVariable final String oib) {
        final Person person = this.personService.getOneByOib(oib);
        log.info("Person fetched successfully by oib");
        return this.personService.getPersonDTOResponseEntity(person);
    }

    @PostMapping()
    public ResponseEntity<PersonDTO> store(@RequestBody final PersonDTO personDTO) {
        final Person person = this.personService.storePerson(this.personService.convertToEntity(personDTO));
        log.info("Person stored permanently");
        return this.personService.savePersonDTOResponseEntity(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@RequestBody final PersonDTO personDTO, @PathVariable final Long id) {
        final Person person = this.personService.updateExistingPerson(this.personService.convertToEntity(personDTO), id);
        log.info("Person updated successfully");
        return this.personService.savePersonDTOResponseEntity(person);
    }

//    @DeleteMapping("/{id}")
//    public HttpStatus delete(@PathVariable final Long id) {
//        final HttpStatus status = this.personService.deletePersonById(id);
//        log.info("Person deleted successfully");
//        return status;
//    }
    @DeleteMapping("/{oib}")
    public HttpStatus delete(@PathVariable final String oib) {
        final HttpStatus status = this.personService.deletePersonByOib(oib);
        log.info("Person deleted successfully");
        return status;
    }
}
