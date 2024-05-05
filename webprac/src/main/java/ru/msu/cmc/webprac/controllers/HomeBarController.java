package ru.msu.cmc.webprac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import ru.msu.cmc.webprac.DAO.ClientsDAO;
import ru.msu.cmc.webprac.DAO.EmployeesDAO;
import ru.msu.cmc.webprac.DAO.Impl.ClientsDAOImpl;
import ru.msu.cmc.webprac.DAO.Impl.EmployeesDAOImpl;
import ru.msu.cmc.webprac.DAO.Impl.ServiceHistoryDAOImpl;
import ru.msu.cmc.webprac.DAO.Impl.ServicesDAOImpl;
import ru.msu.cmc.webprac.DAO.ServiceHistoryDAO;
import ru.msu.cmc.webprac.DAO.ServicesDAO;
import ru.msu.cmc.webprac.models.Clients;
import ru.msu.cmc.webprac.models.Employees;
import ru.msu.cmc.webprac.models.ServiceHistory;
import ru.msu.cmc.webprac.models.Services;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeBarController {
    @Autowired
    private ClientsDAO clientsDAO = new ClientsDAOImpl();
    @Autowired
    private EmployeesDAO employeesDAO = new EmployeesDAOImpl();
    @Autowired
    private ServicesDAO servicesDAO = new ServicesDAOImpl();
    @Autowired
    private ServiceHistoryDAO serviceHistoryDAO = new ServiceHistoryDAOImpl();

    @GetMapping(value = "/clients")
    public String getClients(Model model) {
        List<Clients> clients = (List<Clients>) clientsDAO.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("clientsMethods", clientsDAO);
        return "clients";
    }

    @GetMapping(value = "/employees")
    public String employees(Model model) {
        List<Employees> employees = (List<Employees>) employeesDAO.getAll();
        model.addAttribute("employees", employees);
        model.addAttribute("employeesMethods", employeesDAO);
        return "employees";
    }

    @GetMapping(value = "/services")
    public String services(Model model) {
        List<Services> services = (List<Services>) servicesDAO.getAll();
        model.addAttribute("services", services);
        model.addAttribute("servicesMethods", servicesDAO);
        return "services";
    }

    @GetMapping(value = "/serviceHistory")
    public String serviceHistory(Model model) {
        List<ServiceHistory> serviceHistory = (List<ServiceHistory>) serviceHistoryDAO.getAll();
        model.addAttribute("serviceHistory", serviceHistory);
        model.addAttribute("serviceHistoryMethods", serviceHistoryDAO);
        return "serviceHistory";
   }
}