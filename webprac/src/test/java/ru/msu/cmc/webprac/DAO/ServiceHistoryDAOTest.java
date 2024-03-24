package ru.msu.cmc.webprac.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac.models.Clients;
import ru.msu.cmc.webprac.models.Employees;
import ru.msu.cmc.webprac.models.ServiceHistory;
import ru.msu.cmc.webprac.models.Services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations= "classpath:application.properties")
public class ServiceHistoryDAOTest {
    @Autowired
    private ServiceHistoryDAO serviceHistoryDAO;

    @Autowired
    private ClientsDAO clientsDAO;

    @Autowired
    private EmployeesDAO employeesDAO;

    @Autowired
    private ServicesDAO servicesDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        //getAll
        assertEquals(0, count());

        //get one
        ServiceHistory NotExist = serviceHistoryDAO.getById(100L);
        assertNull(NotExist);

        //add one, save one
        ServiceHistory temp_obj = new ServiceHistory();
        Clients cl = clientsDAO.getById(1L);
        Employees emp = employeesDAO.getById(1L);
        Services serv = servicesDAO.getById(1L);
        Date begin_ = new Date(2024 - 1900, 2, 24);
        temp_obj.setClient_id(cl);
        temp_obj.setEmployee_id(emp);
        temp_obj.setService_id(serv);
        temp_obj.setBegin_(begin_);
        temp_obj.setEnd_(null);
        serviceHistoryDAO.save(temp_obj);
        assertEquals(1, count());

        //add a few, save a few
        List<ServiceHistory> cli = new ArrayList<>();
        ServiceHistory temp_obj1 = new ServiceHistory();
        Clients cl1 = clientsDAO.getById(4L);
        Employees emp1 = employeesDAO.getById(4L);
        Services serv1 = servicesDAO.getById(4L);
        Date begin_1 = new Date(2024 - 1900, 1, 24);
        temp_obj1.setClient_id(cl1);
        temp_obj1.setEmployee_id(emp1);
        temp_obj1.setService_id(serv1);
        temp_obj1.setBegin_(begin_1);
        temp_obj1.setEnd_(null);

        cli.add(temp_obj1);

        ServiceHistory temp_obj2 = new ServiceHistory();
        Clients cl2 = clientsDAO.getById(5L);
        Employees emp2 = employeesDAO.getById(5L);
        Services serv2 = servicesDAO.getById(5L);
        Date begin_2 = new Date(2024 - 1900, 0, 24);
        temp_obj2.setClient_id(cl2);
        temp_obj2.setEmployee_id(emp2);
        temp_obj2.setService_id(serv2);
        temp_obj2.setBegin_(begin_2);
        temp_obj2.setEnd_(null);

        cli.add(temp_obj2);

        serviceHistoryDAO.saveCollection(cli);
        assertEquals(3, count());

        //delete one
        serviceHistoryDAO.delete(cli.get(0));
        assertEquals(2, count());

        //delete by ID
        serviceHistoryDAO.deleteById(3L);
        assertEquals(1, count());

        ServiceHistory serviceHistory = new ServiceHistory();
        serviceHistory.setId(1L);
        Clients cl4 = serviceHistoryDAO.getById(1L).getClient_id();
        Employees emp4 = employeesDAO.getById(5L);
        emp4.setName("CHANGE IN SERVICE_HISTORY");
        Services serv4 = servicesDAO.getById(5L);
        Date begin_4 = serviceHistoryDAO.getById(1L).getBegin_();
        Date end_4 = new Date(2024-1900, 3, 20);
        serviceHistory.setClient_id(cl4);
        serviceHistory.setEmployee_id(emp4);
        serviceHistory.setService_id(serv4);
        serviceHistory.setBegin_(begin_4);
        serviceHistory.setEnd_(end_4);
        //update
        serviceHistoryDAO.update(serviceHistory);
        assertEquals(1, count());

        serviceHistoryDAO.saveCollection(cli);
        assertEquals(3, count());

        ServiceHistory temp_obj3 = new ServiceHistory();
        Clients cl3 = new Clients("Иванов TEST Иванович", "Иванов Иванович", "123 Main St", "123-456-7890", "ivanov@example.com");
        clientsDAO.save(cl3);
        Employees emp3 = employeesDAO.getById(6L);
        Services serv3 = servicesDAO.getById(5L);
        Date begin_3 = new Date(2024 - 1900, 0, 24);
        temp_obj3.setClient_id(cl3);
        temp_obj3.setEmployee_id(emp3);
        temp_obj3.setService_id(serv3);
        temp_obj3.setBegin_(begin_3);
        temp_obj3.setEnd_(null);
        serviceHistoryDAO.save(temp_obj3);

        ServiceHistory temp_obj4 = new ServiceHistory();
        Clients cl5 = new Clients("Иванов TEST Иванович", "Иванов TEST Иванович", "123 Main", "123-456-7890", "ivanov@example.com");
        clientsDAO.save(cl5);
        Employees emp5 = employeesDAO.getById(6L);
        Services serv5 = servicesDAO.getById(6L);
        Date begin_5 = new Date(2024 - 1900, 0, 24);
        temp_obj4.setClient_id(cl5);
        temp_obj4.setEmployee_id(emp5);
        temp_obj4.setService_id(serv5);
        temp_obj4.setBegin_(begin_5);
        temp_obj4.setEnd_(null);
        serviceHistoryDAO.save(temp_obj4);

        ServiceHistory temp_obj5 = new ServiceHistory();
        Clients cl6 = new Clients("Иванов TEST Иванович", "Иванов TEST Иванович", "123 Main St", "123-456-0987", "ivanov@example.com");
        clientsDAO.save(cl6);
        Employees emp6 = employeesDAO.getById(7L);
        Services serv6 = servicesDAO.getById(7L);
        Date begin_6 = new Date(2024 - 1900, 0, 24);
        temp_obj5.setClient_id(cl6);
        temp_obj5.setEmployee_id(emp6);
        temp_obj5.setService_id(serv6);
        temp_obj5.setBegin_(begin_6);
        temp_obj5.setEnd_(null);
        serviceHistoryDAO.save(temp_obj5);

        ServiceHistory temp_obj6 = new ServiceHistory();
        Clients cl7 = new Clients("Иванов TEST Иванович", "Иванов TEST Иванович", "123 Main St", "123-456-0987", "ivanov@exe.com");
        clientsDAO.save(cl7);
        Employees emp7 = employeesDAO.getById(8L);
        Services serv7 = servicesDAO.getById(8L);
        Date begin_7 = new Date(2024 - 1900, 0, 24);
        temp_obj6.setClient_id(cl7);
        temp_obj6.setEmployee_id(emp7);
        temp_obj6.setService_id(serv7);
        temp_obj6.setBegin_(begin_7);
        temp_obj6.setEnd_(null);
        serviceHistoryDAO.save(temp_obj6);

        ClientsDAO.Filter filter1 = new ClientsDAO.Filter("Иванов TEST Иванович", "Иванов TEST Иванович", "123 Main St", "123-456-7890", null);
        EmployeesDAO.Filter filter2 = new EmployeesDAO.Filter(null, null, null, null, "Test1");
        ServicesDAO.Filter filter3 = new ServicesDAO.Filter(null, 100F);
        List<ServiceHistory> view = (List<ServiceHistory>) serviceHistoryDAO.getByFilter(filter1, filter2, filter3);
        assertEquals(1, view.size());

    }
    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE website_test.public.servicehistory RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }
    }

    public Integer count() {
        List<ServiceHistory> ListAll = (List<ServiceHistory>) serviceHistoryDAO.getAll();
        return ListAll.size();
    }
}
