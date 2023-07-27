package hr.rba.creditcardapplication;

import hr.rba.creditcardapplication.models.entities.Person;

import java.util.Arrays;
import java.util.List;

public class MockEntityDataValues {

    public static final String OIB_1 = "45485474525";
    public static final String OIB_2 = "14585474526";
    public static final String OIB_3 = "96585474236";
    private static final Person PERSON_1 = new Person(1L, OIB_1, "Pero", "Peric", "Aktivan");

    private static final Person PERSON_2 = new Person(2L, OIB_2, "Nino", "Ninic", "Neaktivan");

    private static final Person PERSON_3 = new Person(3L, OIB_3, "Ivo", "Ivic", "Aktivan");

    public static List<Person> personEntities() {
        return Arrays.asList(PERSON_1, PERSON_2, PERSON_3);
    }
}
