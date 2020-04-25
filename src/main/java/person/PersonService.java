package person;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import person.model.Address;
import person.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.ZoneId;
import java.util.Collection;

@Log4j2
public class PersonService {

    public EntityManager em;
    Faker faker = new Faker();
    public PersonService(EntityManager em) {
        this.em = em;
    }

    public Person randomPerson() {
        Person person = new Person();
        Address address = new Address();

        address.setCity(faker.address().city());
        address.setCountry(faker.address().country());
        address.setState(faker.address().state());
        address.setStreetAddress(faker.address().streetAddress());
        address.setZip(faker.address().zipCode());

        person.setName(faker.name().name());
        person.setGender(faker.options().option(Person.Gender.class));
        person.setDob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        person.setAddress(address);
        person.setEmail(faker.internet().emailAddress());
        person.setProfession(faker.company().profession());

        return person;
    }

    public Collection<Person> findAllPersons() {
        Query query = em.createQuery("SELECT p FROM Person p");
        return (Collection<Person>) query.getResultList();
    }
}
