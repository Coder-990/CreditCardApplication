package hr.rba.creditcardapplication;

import hr.rba.creditcardapplication.models.dtos.FileDTO;
import hr.rba.creditcardapplication.models.dtos.PersonDTO;

import java.util.Arrays;
import java.util.List;

import static hr.rba.creditcardapplication.util.Constants.ACTIVE;

public class MockDtoDataValues {
    public static final String OIB_1 = "45485474525";
    public static final String OIB_2 = "14585474526";
    public static final String OIB_3 = "96585474236";
    private static final FileDTO FILE_1 = FileDTO.builder().id(1L).status(ACTIVE).build();
    private static final FileDTO FILE_2 = FileDTO.builder().id(2L).status(ACTIVE).build();
    private static final FileDTO FILE_3 = FileDTO.builder().id(3L).status(ACTIVE).build();
    public static final PersonDTO PERSON_DTO_1 = PersonDTO.builder().id(1L).oib(OIB_1).name("Pero").lastName("Peric").file(FILE_1).build();
    public static final PersonDTO PERSON_DTO_2 = PersonDTO.builder().id(1L).oib(OIB_2).name("Nino").lastName("Ninic").file(FILE_2).build();
    public static final PersonDTO PERSON_DTO_3 = PersonDTO.builder().id(1L).oib(OIB_3).name("Ivo").lastName("Ivic").file(FILE_3).build();

    public static List<PersonDTO> personDtos() {
        return Arrays.asList(PERSON_DTO_1, PERSON_DTO_2, PERSON_DTO_3);
    }
}
