package hr.rba.creditcardapplication;

import hr.rba.creditcardapplication.models.entities.File;
import hr.rba.creditcardapplication.models.entities.Person;

import java.util.Arrays;
import java.util.List;

import static hr.rba.creditcardapplication.services.impl.FileServiceImpl.ACTIVE;
import static hr.rba.creditcardapplication.services.impl.FileServiceImpl.INACTIVE;

public class MockEntityDataValues {

    public static final String OIB_1 = "45485474525";
    public static final String OIB_2 = "14585474526";
    public static final String OIB_3 = "96585474236";
    private static final Person PERSON_1 = new Person(1L, OIB_1, "Pero", "Peric", new File(1L, ACTIVE));

    private static final Person PERSON_2 = new Person(2L, OIB_2, "Nino", "Ninic", new File(2L, INACTIVE));

    private static final Person PERSON_3 = new Person(3L, OIB_3, "Ivo", "Ivic", new File(3L, ACTIVE));

    public static List<Person> personEntities() {
        return Arrays.asList(PERSON_1, PERSON_2, PERSON_3);
    }
}
