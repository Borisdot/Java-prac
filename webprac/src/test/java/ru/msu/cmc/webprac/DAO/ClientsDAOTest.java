package ru.msu.cmc.webprac.DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac.models.Clients;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations= "classpath:application.properties")
public class ClientsDAOTest {

    @Autowired
    private ClientsDAO clientsDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        //getAll
        assertEquals(0, count());

        //get one
        Clients personNotExist = clientsDAO.getById(100L);
        assertNull(personNotExist);

        //add one, save one
        Clients temp_obj = new Clients("Иванов TEST Иванович",
                "Иванов TEST Иванович",
                "123 Main St",
                "123-456-7890",
                "ivanov@example.com");
        clientsDAO.save(temp_obj);
        assertEquals(1, count());

        //add a few, save a few
        List<Clients> cli = new ArrayList<>();
        cli.add(new Clients("Test1", "Test1", "Test1", "Test1", "Test1"));
        cli.add(new Clients("Test2", "Test2", "Test2", "Test2", "Test2"));
        cli.add(new Clients("Test3", "Test3", "Test3", "Test3", "Test3"));
        clientsDAO.saveCollection(cli);
        assertEquals(4, count());

        //delete one
        clientsDAO.delete(cli.get(0));
        assertEquals(3, count());

        //delete by ID
        clientsDAO.deleteById(3L);
        assertEquals(2, count());

        Clients clients = new Clients(4L, "UPDATE", "UPDATE", "UPDATE", "UPDATE", "UPDATE");

        //update
        clientsDAO.update(clients);
        assertEquals(2, count());

        //add new values for test of search
        List<Clients> cli_dop = new ArrayList<>();
        cli_dop.add(new Clients("Search1", "Search1", "Anytown12345", "Test1", "Test1"));
        cli_dop.add(new Clients("Search2", "Search2", "Anytown12", "Search2", "Search2"));
        cli_dop.add(new Clients("Search3", "Search3", "Anytown5", "Search3", "Search3"));
        cli_dop.add(new Clients("Search3", "Search666", "Anytown5", "Search3", "Search3"));
        cli_dop.add(new Clients("Search3", "Search3", "Anytown5", "Search666", "Search3"));
        clientsDAO.saveCollection(cli_dop);
        assertEquals(7, count());

        //create filter
        ClientsDAO.Filter filter = new ClientsDAO.Filter(null, null,
                                                        "Anytown12",null,null);
        //search by filter
        List<Clients> view = clientsDAO.getByFilter(filter);
        assertEquals(1, view.size());
        assertEquals("Search2", view.get(0).getName());
        assertEquals("Search2", view.get(0).getContact());
        assertEquals("Anytown12", view.get(0).getAddress());

        //create filter 2
        ClientsDAO.Filter filter2 = new ClientsDAO.Filter("Search3", "Search3",
                "Anytown5","Search3","Search6666");

        List<Clients> view1 = clientsDAO.getByFilter(filter2);
        assertEquals(0, view1.size());

        //100% coverage -> try to save with BAD ID
        Clients clients2 = new Clients(4456456556L, "BAD_ID", "BAD_ID", "BAD_ID", "BAD_ID", "BAD_ID");
        clientsDAO.save(clients2);
        assertEquals(8, count());
    }

    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE Clients RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    public Integer count() {
        List<Clients> ListAll = (List<Clients>) clientsDAO.getAll();
        return ListAll.size();
    }
}
