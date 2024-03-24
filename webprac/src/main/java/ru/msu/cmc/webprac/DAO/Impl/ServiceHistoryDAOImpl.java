package ru.msu.cmc.webprac.DAO.Impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ClientsDAO;
import ru.msu.cmc.webprac.DAO.EmployeesDAO;
import ru.msu.cmc.webprac.DAO.ServiceHistoryDAO;
import ru.msu.cmc.webprac.DAO.ServicesDAO;
import ru.msu.cmc.webprac.models.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ServiceHistoryDAOImpl extends CommonDAOImpl<ServiceHistory, Long> implements ServiceHistoryDAO {

    public ServiceHistoryDAOImpl() {
        super(ServiceHistory.class);
    }

    @Override
    public void update(ServiceHistory entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employees employees = entity.getEmployee_id();
            Services services = entity.getService_id();
            session.update(employees);
            session.update(services);
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public Collection<ServiceHistory> getByFilter(ClientsDAO.Filter filter1,
                                                 EmployeesDAO.Filter filter2,
                                                 ServicesDAO.Filter filter3) {
        List<ServiceHistory> sorted = (List<ServiceHistory>) getAll();
        List<ServiceHistory> new_sorted = new ArrayList<>();
        for (ServiceHistory serviceHistory : sorted) {
            Clients clients = serviceHistory.getClient_id();
            Employees employees = serviceHistory.getEmployee_id();
            Services services = serviceHistory.getService_id();

            //check of client
            if (filter1.getName() != null && !clients.getName().equals(filter1.getName())) {
                continue;
            }
            if (filter1.getContact() != null && !clients.getContact().equals(filter1.getContact())) {
                continue;
            }
            if (filter1.getAddress() != null && !clients.getAddress().equals(filter1.getAddress())) {
                continue;
            }
            if (filter1.getPhone() != null && !clients.getPhone().equals(filter1.getPhone())) {
                continue;
            }
            if (filter1.getEmail() != null && !clients.getEmail().equals(filter1.getEmail())) {
                continue;
            }

            //check of employee
            if (employees != null) {
                if (filter2.getName() != null && !employees.getName().equals(filter2.getName())) {
                    continue;
                }
                if (filter2.getAddress() != null && !employees.getAddress().equals(filter2.getAddress())) {
                    continue;
                }
                if (filter2.getPhone() != null && !employees.getPhone().equals(filter2.getPhone())) {
                    continue;
                }
                if (filter2.getEmail() != null && !employees.getEmail().equals(filter2.getEmail())) {
                    continue;
                }
                if (filter2.getFunction_() != null && !employees.getFunction_().equals(filter2.getFunction_())) {
                    continue;
                }
            }

            //check of service
            if (services != null) {
                if (filter3.getName() != null && !services.getName().equals(filter3.getName())) {
                    continue;
                }
                if (filter3.getCost() != null && !services.getCost().equals(filter3.getCost())) {
                    continue;
                }
            }
            new_sorted.add(serviceHistory);
        }
        return new_sorted;
    }
}
