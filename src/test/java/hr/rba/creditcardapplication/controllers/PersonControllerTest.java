package hr.rba.creditcardapplication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.rba.creditcardapplication.MockDtoDataValues;
import hr.rba.creditcardapplication.MockEntityDataValues;
import hr.rba.creditcardapplication.models.dtos.FileDTO;
import hr.rba.creditcardapplication.models.dtos.PersonDTO;
import hr.rba.creditcardapplication.models.entities.File;
import hr.rba.creditcardapplication.models.entities.Person;
import hr.rba.creditcardapplication.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static hr.rba.creditcardapplication.MockEntityDataValues.*;
import static hr.rba.creditcardapplication.util.Constants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    public static final String API_V_1_PERSONS_BASE_URL = "/api/v1/person/";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;
    @Autowired
    private ObjectMapper objectMapper;
    private List<PersonDTO> personDTOS;
    private List<Person> persons;
    private PersonDTO personDTO;
    private Person person;
    private File file;
    private FileDTO fileDTO;

    @BeforeEach
    void setup() {
        personDTOS = MockDtoDataValues.personDtos();
        persons = MockEntityDataValues.personEntities();
        personDTO = MockDtoDataValues.PERSON_DTO_2;
        fileDTO = MockDtoDataValues.PERSON_DTO_1.getFile();
        person = MockEntityDataValues.PERSON_2;
        file = MockEntityDataValues.PERSON_1.getFile();
    }

    @Test
    void testGetAll() throws Exception {
        when(personService.getAll()).thenReturn(persons);
        mockMvc.perform(get(API_V_1_PERSONS_BASE_URL.concat(GET_ALL))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(personService, times(1)).getAll();
    }

    @Test
    void testGetAllEmpty() throws Exception {
        when(personService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(API_V_1_PERSONS_BASE_URL.concat(GET_ALL))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(personService, times(1)).getAll();
    }

    @Test
    void getByOib() throws Exception {
        when(personService.getOneByOib(OIB_2)).thenReturn(person);
        mockMvc.perform(get(API_V_1_PERSONS_BASE_URL.concat(GET_BY.concat(OIB_2)))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GIVEN person record does not exist in database, WHEN new person record is created, THEN new record is created and returned.")
    void testStore() throws Exception {
        given(personService.storePerson(ArgumentMatchers.any()))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(personService.storePerson(person)).thenReturn(person);
        mockMvc.perform(post(API_V_1_PERSONS_BASE_URL.concat(STORE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(personService, times(1)).storePerson(any());
    }

    @Test
    void update() {
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(API_V_1_PERSONS_BASE_URL.concat(REMOVE_BY.concat(OIB_3))))
                .andExpect(status().isOk());

    }
}