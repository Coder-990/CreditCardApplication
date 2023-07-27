package hr.rba.creditcardapplication.services.impl;

import hr.rba.creditcardapplication.MockEntityDataValues;
import hr.rba.creditcardapplication.PersonFileWriter;
import hr.rba.creditcardapplication.exceptions.PersonNotFoundRuntimeException;
import hr.rba.creditcardapplication.models.entities.Person;
import hr.rba.creditcardapplication.repositories.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static hr.rba.creditcardapplication.MockEntityDataValues.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;
    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setup() {
        personService = new PersonServiceImpl(personRepository, new ModelMapper(), new PersonFileWriter());
    }

    @Nested
    @DisplayName("PersonService get all persons")
    class PersonServiceTestAllPersons {

        @Test
        @DisplayName("GIVEN person records exists in database, WHEN all person records are requested, THEN all persons from database are returned.")
        void testGetAll() {
            final List<Person> expectedList = MockEntityDataValues.personEntities();
            when(personRepository.findAll()).thenReturn(expectedList);
            final List<Person> actualList = personService.getAll();
            assertAll(() -> assertNotNull(actualList),
                    () -> assertEquals(expectedList, actualList));
        }

        @Test
        @DisplayName("GIVEN there are no person records in database, WHEN all person records are requested, THEN empty list is returned.")
        void testGetAllEmpty() {
            final List<Person> expectedList = Collections.emptyList();
            when(personRepository.findAll()).thenReturn(Collections.emptyList());
            final List<Person> actualList = personService.getAll();
            assertAll(() -> assertNotNull(actualList),
                    () -> assertEquals(expectedList, actualList));
        }
    }

    @Nested
    @DisplayName("PersonService get person by oib")
    class PersonServiceTestGetOnePersonByOib {

        @Test
        @DisplayName("GIVEN person record exists in database, WHEN a single person record is fetched, THEN the person with requested OIB is returned.")
        void testGetOneByOib() {
            final Person expectedPerson = MockEntityDataValues.personEntities().get(1);
            when(personRepository.findByOib(OIB_2))
                    .thenReturn(MockEntityDataValues.personEntities().stream()
                            .filter(person -> person.getOib().equals(OIB_2))
                            .findFirst());
            final Person actualPerson = personService.getOneByOib(OIB_2);
            assertAll(() -> assertNotNull(actualPerson),
                    () -> assertEquals(expectedPerson, actualPerson));
        }

        @Test
        @DisplayName("GIVEN person record does not exists in database, WHEN a single person record is fetched, THEN error is thrown.")
        void testGetOneByNonExistingOib() {
            Class<PersonNotFoundRuntimeException> expectedExceptionClass = PersonNotFoundRuntimeException.class;
            when(personRepository.findByOib(OIB_2)).thenThrow(new PersonNotFoundRuntimeException(OIB_2));
            Executable executable = () -> personService.getOneByOib(OIB_2);
            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("PersonService create person")
    class PersonServiceTestStorePerson {

        @Test
        @DisplayName("GIVEN person record does not exist in database, WHEN new person record is created, THEN new record is created and returned.")
        void testStorePerson() {
            final Person expectedPerson = MockEntityDataValues.personEntities().get(2);
            when(personRepository.save(any(Person.class))).thenReturn(expectedPerson);
            final Person actualPerson = personService.storePerson(expectedPerson);
            assertAll(() -> assertNotNull(actualPerson),
                    () -> assertEquals(expectedPerson, actualPerson));
        }
    }

    @Nested
    @DisplayName("PersonService update person")
    class PersonServiceTestUpdatePerson {

        @Test
        @DisplayName("GIVEN person record exists in database, WHEN a person record is updated, THEN person record is updated and returned.")
        void testUpdateExistingPerson() {
            final Person expectedPerson = MockEntityDataValues.personEntities().get(2);
            when(personRepository.findById(1L))
                    .thenReturn(MockEntityDataValues.personEntities().stream()
                            .filter(person -> person.getId() == 1L).findFirst());
            when(personRepository.save(any(Person.class))).thenReturn(expectedPerson);
            final Person updateActualPerson = personService.updateExistingPerson(expectedPerson, 1L);
            assertAll(() -> assertNotNull(updateActualPerson),
                    () -> assertEquals(expectedPerson, updateActualPerson));
        }

        @Test
        @DisplayName("GIVEN person record does not exist in database, WHEN a person record is updated, THEN error is thrown.")
        void testUpdateNonExistingPerson() {
            final Person expectedPerson = MockEntityDataValues.personEntities().get(2);
            Class<PersonNotFoundRuntimeException> expectedExceptionClass = PersonNotFoundRuntimeException.class;
            when(personRepository.findById(1L)).thenThrow(expectedExceptionClass);
            Executable executable = () -> personService.updateExistingPerson(expectedPerson, 1L);
            assertThrows(expectedExceptionClass, executable);
        }
    }

    @Nested
    @DisplayName("PersonServiceTest delete person")
    class PersonServiceTestDeletePerson {
        @Test
        @DisplayName("GIVEN person record either exist or not, WHEN a single person record is deleted, THEN repository delete method should be called once.")
        void testDeletePersonById() {
            final Long personId = MockEntityDataValues.personEntities().get(0).getId();
            personRepository.removePersonById(personId);
            verify(personRepository, times(1)).removePersonById(personId);
        }

        @Test
        @DisplayName("GIVEN person record either exist or not, WHEN a single person record is deleted, THEN repository delete method should be called once.")
        void testDeletePersonByOib() {
            final String personOib = MockEntityDataValues.personEntities().get(0).getOib();
            personRepository.removePersonByOib(personOib);
            verify(personRepository, times(1)).removePersonByOib(personOib);
        }
    }
}