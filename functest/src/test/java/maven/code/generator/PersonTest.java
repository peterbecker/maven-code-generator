package maven.code.generator;

import my.testPackage.Person;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class PersonTest {
    @Test
    public void testGetters() {
        Person person = new Person("Name", new Date(1919,9,19));
        assertEquals("Name", person.getName());
        assertEquals(new Date(1919,9,19), person.getBirthDate());
    }
}
