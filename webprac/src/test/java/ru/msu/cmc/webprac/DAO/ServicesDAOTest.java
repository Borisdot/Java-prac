package ru.msu.cmc.webprac.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac.models.Services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations= "classpath:application.properties")
public class ServicesDAOTest {
    @Autowired
    private ServicesDAO servicesDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        //getAll
        assertEquals(0, count());

        //get one
        Services NotExist = servicesDAO.getById(100L);
        assertNull(NotExist);

        //add one, save one
        Services temp_obj = new Services("TEST", 150F);
        servicesDAO.save(temp_obj);
        assertEquals(1, count());

        //add a few, save a few
        List<Services> cli = new ArrayList<>();
        cli.add(new Services("Test1", 100F));
        cli.add(new Services("Test2", 200F));
        cli.add(new Services("Test3", 300F));
        servicesDAO.saveCollection(cli);
        assertEquals(4, count());

        //delete one
        servicesDAO.delete(cli.get(0));
        assertEquals(3, count());

        //delete by ID
        servicesDAO.deleteById(3L);
        assertEquals(2, count());

        Services services = new Services(4L, "UPDATE", 666F);

        //update
        servicesDAO.update(services);
        assertEquals(2, count());

        //add new values for test of search
        List<Services> cli_dop = new ArrayList<>();
        cli_dop.add(new Services("Search1", 100F));
        cli_dop.add(new Services("Search2", 200F));
        cli_dop.add(new Services("Search3", 300F));
        cli_dop.add(new Services("Search5", 400F));
        cli_dop.add(new Services("Search5", 228F));
        servicesDAO.saveCollection(cli_dop);
        assertEquals(7, count());

        //create filter
        ServicesDAO.Filter filter = new ServicesDAO.Filter("Search5", 228F);
        List<Services> view = servicesDAO.getByFilter(filter);
        assertEquals(1, view.size());
        assertEquals("Search5", view.get(0).getName());

    }
    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
//            session.createQuery("TRUNCATE website_test.public.services RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE website_test.public.services RESTART IDENTITY CASCADE").executeUpdate();
            session.getTransaction().commit();
        }
    }

    public Integer count() {
        List<Services> ListAll = (List<Services>) servicesDAO.getAll();
        return ListAll.size();
    }
}
