package ru.msu.cmc.webprac.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac.models.Employees;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations= "classpath:application.properties")
public class EmployeesDAOTest {
    @Autowired
    private EmployeesDAO employeesDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        //getAll
        assertEquals(0, count());

        //get one
        Employees personNotExist = employeesDAO.getById(100L);
        assertNull(personNotExist);

        //add one, save one
        Employees temp_obj = new Employees("Иванов TEST Иванович",
                "123 Main St",
                "123-456-7890",
                "ivanov@example.com",
                "lawyer");
        employeesDAO.save(temp_obj);
        assertEquals(1, count());

        //add a few, save a few
        List<Employees> cli = new ArrayList<>();
        cli.add(new Employees("Test1", "Test1", "Test1", "Test1", "Test1"));
        cli.add(new Employees("Test2", "Test2", "Test2", "Test2", "Test2"));
        cli.add(new Employees("Test3", "Test3", "Test3", "Test3", "Test3"));
        employeesDAO.saveCollection(cli);
        assertEquals(4, count());

        //delete one
        employeesDAO.delete(cli.get(0));
        assertEquals(3, count());

        //delete by ID
        employeesDAO.deleteById(3L);
        assertEquals(2, count());

        Employees employees = new Employees(4L, "UPDATE", "UPDATE", "UPDATE", "UPDATE", "UPDATE");

        //update
        employeesDAO.update(employees);
        assertEquals(2, count());

        //add new values for test of search
        List<Employees> cli_dop = new ArrayList<>();
        cli_dop.add(new Employees("Search1", "Search1", "Anytown12345", "Test1", "Test1"));
        cli_dop.add(new Employees("Search2", "Search2", "Anytown12", "Search2", "Search2"));
        cli_dop.add(new Employees("Search3", "Search3", "Anytown5", "Search3", "Search3"));
        cli_dop.add(new Employees("Search3", "Search666", "Anytown5", "Search3", "Search3"));
        cli_dop.add(new Employees("Search3", "Search3", "Anytown5", "Search666", "Search3"));
        employeesDAO.saveCollection(cli_dop);
        assertEquals(7, count());

        //create filter
        EmployeesDAO.Filter filter = new EmployeesDAO.Filter(null, null, "Anytown5",
                                                              "Search3", null);
        List<Employees> view = employeesDAO.getByFilter(filter);
        assertEquals(2, view.size());
        assertEquals("Search3", view.get(0).getAddress());
        assertEquals("Search666", view.get(1).getAddress());

        //create filter 2
        EmployeesDAO.Filter filter2 = new EmployeesDAO.Filter("Search3", "Search3",
                "Anytown5","Search3","Search6666");

        List<Employees> view1 = employeesDAO.getByFilter(filter2);
        assertEquals(0, view1.size());

    }
    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE website_test.public.employees RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    public Integer count() {
        List<Employees> ListAll = (List<Employees>) employeesDAO.getAll();
        return ListAll.size();
    }
}
