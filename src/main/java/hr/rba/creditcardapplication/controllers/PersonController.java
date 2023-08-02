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

import static hr.rba.creditcardapplication.util.Constants.*;

@Slf4j
@RestController
@RequestMapping(PersonController.BASE_URL)
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PersonController {

    public static final String BASE_URL = "api/v1/persons";
    public static final String OIB = "/{oib}";
    public static final String ID = "/{id}";

    private final PersonService personService;

    @GetMapping()
    public List<PersonDTO> getAll() {
        final List<PersonDTO> personDTOS = this.personService.getAll()
                .stream().map(this.personService::convertToDto).toList();
        if (personDTOS.isEmpty()) {
            log.info(EMPTY_LIST);
        } else {
            log.info(PERSONS + INITIALIZED_SUCCESSFULLY);
        }
        return personDTOS;
    }

    @GetMapping(OIB)
    public ResponseEntity<PersonDTO> getByOib(@PathVariable final String oib) {
        final Person person = this.personService.getOneByOib(oib);
        log.info(PERSON + SUCCESSFULLY_BY_OIB);
        return this.personService.getPersonDTOResponseEntity(person);
    }

    @PostMapping()
    public ResponseEntity<PersonDTO> store(@RequestBody final PersonDTO personDTO) {
        final Person person = this.personService.storePerson(this.personService.convertToEntity(personDTO));
        log.info(PERSON + STORED_PERMANENTLY);
        return this.personService.savePersonDTOResponseEntity(person);
    }

    @PutMapping(ID)
    public ResponseEntity<PersonDTO> update(@RequestBody final PersonDTO personDTO, @PathVariable final Long id) {
        final Person person = this.personService.updateExistingPerson(this.personService.convertToEntity(personDTO), id);
        log.info(PERSON + UPDATED_SUCCESSFULLY);
        return this.personService.savePersonDTOResponseEntity(person);
    }

    @DeleteMapping(OIB)
    public HttpStatus delete(@PathVariable final String oib) {
        final HttpStatus status = this.personService.deletePersonByOib(oib);
        log.info(PERSON + DELETED_SUCCESSFULLY);
        return status;
    }
}
