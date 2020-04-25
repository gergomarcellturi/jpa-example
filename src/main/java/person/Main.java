package person;

import person.model.Person;
import org.tinylog.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class Main {



    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();
        PersonService service = new PersonService(em);
        try {
            em.getTransaction().begin();
            for ( int i = 0; i < 1000; i++ ) {
                em.persist(service.randomPerson());
            }
        } finally {
            em.getTransaction().commit();
        }

        for (Person p : service.findAllPersons())
            Logger.info(p);


        em.close();
    }
}
