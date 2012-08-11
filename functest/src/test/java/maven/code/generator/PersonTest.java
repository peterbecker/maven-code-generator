package maven.code.generator;

import my.testPackage.Person;
import my.testPackage.PersonDao;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class PersonTest {
    @Test
    public void testSaveAndLoad() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test");
        EntityManager em = factory.createEntityManager();
        PersonDao dao = new PersonDao(em);

        Person newPerson = new Person("Someone", new Date(1990,10,10));
        dao.save(newPerson);
        long newPersonId = newPerson.getId();

        Person samePerson = dao.load(newPersonId);
        assertEquals("Names should be equal", newPerson.getName(), samePerson.getName());
        assertEquals("Birthdates should be equal", newPerson.getBirthDate(), samePerson.getBirthDate());
    }

    @Test
    public void testGetters() {
        Person person = new Person("Name", new Date(1919,9,19));
        assertEquals("Name", person.getName());
        assertEquals(new Date(1919,9,19), person.getBirthDate());
    }
}
