package ru.msu.cmc.webprac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ServiceHistoryController {
    @Autowired
    private final ServiceHistoryDAO serviceHistoryDAO = new ServiceHistoryDAOImpl();
    @Autowired
    private final ClientsDAO clientsDAO = new ClientsDAOImpl();
    @Autowired
    private final EmployeesDAO employeesDAO = new EmployeesDAOImpl();
    @Autowired
    private final ServicesDAO servicesDAO = new ServicesDAOImpl();

    @PostMapping(value = "/serviceHistory/add")
    public String Add( @RequestParam(required = false) String client_Name,
                       @RequestParam(required = false) String contact_person,
                       @RequestParam(required = false) String address_client,
                       @RequestParam(required = false) String phone_client,
                       @RequestParam(required = false) String email_client,
                       @RequestParam(required = false) String employee_Name,
                       @RequestParam(required = false) String address_employee,
                       @RequestParam(required = false) String phone_employee,
                       @RequestParam(required = false) String email_employee,
                       @RequestParam(required = false) String function_,
                       @RequestParam(required = false) String service_Name,
                       @RequestParam(required = false) String cost,
                       @RequestParam(required = false) String begin_,
                       @RequestParam(required = false) String end_,
                       Model model) {
        client_Name = emptyToNull(client_Name);
        contact_person = emptyToNull(contact_person);
        address_client = emptyToNull(address_client);
        phone_client = emptyToNull(phone_client);
        email_client = emptyToNull(email_client);
        employee_Name = emptyToNull(employee_Name);
        address_employee = emptyToNull(address_employee);
        phone_employee = emptyToNull(phone_employee);
        email_employee = emptyToNull(email_employee);
        function_ = emptyToNull(function_);
        service_Name = emptyToNull(service_Name);
        cost = emptyToNull(cost);
        begin_ = emptyToNull(begin_);
        end_ = emptyToNull(end_);

        Clients result_client = null;
        Employees result_employee = null;
        Services result_services = null;
        Date result_begin = null;
        Date result_end = null;

        //берем id client
        ClientsDAO.Filter filter_1 = new ClientsDAO.Filter(client_Name, contact_person, address_client, phone_client, email_client);
        List<Clients> temp1 = clientsDAO.getByFilter(filter_1);
        if (temp1.size() == 0 || temp1.size() > 1) {
            model.addAttribute("error_msg", "Уточните параметры клиента при добавлении записи об оказанных услугах");
            return "errorPage";
        }
        result_client = temp1.get(0);


        //МОЖЕТ БЫТЬ NULL
        //берем id employee
        if (employee_Name == null && address_employee == null && phone_employee == null && email_employee == null && function_ == null) {
            result_employee = null;
        } else {
            EmployeesDAO.Filter filter_2 = new EmployeesDAO.Filter(employee_Name, address_employee, phone_employee, email_employee, function_);
            List<Employees> temp2 = employeesDAO.getByFilter(filter_2);
            if (temp2.size() == 0 || temp2.size() > 1) {
                model.addAttribute("error_msg", "Уточните параметры служащего при добавлении записи об оказанных услугах");
                return "errorPage";
            }
            result_employee = temp2.get(0);
        }

        //МОЖЕТ БЫТЬ NULL
        //берем id service
        if (service_Name == null && cost == null) {
            result_services = null;
        } else {
            ServicesDAO.Filter filter_3 = null;
            if (cost == null) {
                filter_3 = new ServicesDAO.Filter(service_Name, null);
            } else {
                Float f_cost = 0.0F;
                try {
                    f_cost = Float.parseFloat(cost);
                } catch (NumberFormatException e) {
                    model.addAttribute("error_msg", "Некорректная стоимость при добавлении оказанной услуги");
                    return "errorPage";
                }
                filter_3 = new ServicesDAO.Filter(service_Name, f_cost);
            }
            List<Services> temp_3 = servicesDAO.getByFilter(filter_3);
            if (temp_3.size() == 0 || temp_3.size() > 1) {
                model.addAttribute("error_msg", "Уточните параметры услуги при добавлении записи об оказанных услугах");
                return "errorPage";
            }
            result_services = temp_3.get(0);
        }
        if (begin_ == null) {
            result_begin = null;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // Преобразование строки в java.util.Date
                java.util.Date utilDate = sdf.parse(begin_);

                // Преобразование java.util.Date в java.sql.Date
                result_begin = new Date(utilDate.getTime());
            } catch (ParseException e) {
                model.addAttribute("error_msg", "Неверный формат даты при добавлении записи об оказанных услугах");
                return "errorPage";
            }
        }

        if (end_ == null) {
            result_end = null;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // Преобразование строки в java.util.Date
                java.util.Date utilDate = sdf.parse(end_);

                // Преобразование java.util.Date в java.sql.Date
                result_end = new Date(utilDate.getTime());
            } catch (ParseException e) {
                model.addAttribute("error_msg", "Неверный формат даты при добавлении записи об оказанных услугах");
                return "errorPage";
            }
        }

        ServiceHistory copy = new ServiceHistory();
        copy.setClient_id(result_client);
        copy.setEmployee_id(result_employee);
        copy.setService_id(result_services);
        copy.setBegin_(result_begin);
        copy.setEnd_(result_end);
        if (!serviceHistoryDAO.ifThereIsSuchItem(copy)) {
            model.addAttribute("error_msg", "Уже есть такая запись об оказанных услугах");
            return "errorPage";
        }

        serviceHistoryDAO.save(copy);
        List<ServiceHistory> serviceHistory = (List<ServiceHistory>) serviceHistoryDAO.getAll();
        model.addAttribute("serviceHistory", serviceHistory);
        model.addAttribute("serviceHistoryMethods", serviceHistoryDAO);
        return "serviceHistory";
    }

    @GetMapping(value = "/serviceHistory/search")
    public String Search(@RequestParam(required = false) String client_Name,
                         @RequestParam(required = false) String contact_person,
                         @RequestParam(required = false) String address_client,
                         @RequestParam(required = false) String phone_client,
                         @RequestParam(required = false) String email_client,
                         @RequestParam(required = false) String employee_Name,
                         @RequestParam(required = false) String address,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String function_,
                         @RequestParam(required = false) String service_Name,
                         @RequestParam(required = false) String cost,
                         Model model) {
        client_Name = emptyToNull(client_Name);
        contact_person = emptyToNull(contact_person);
        address_client = emptyToNull(address_client);
        phone_client = emptyToNull(phone_client);
        email_client = emptyToNull(email_client);
        employee_Name = emptyToNull(employee_Name);
        address = emptyToNull(address);
        phone = emptyToNull(phone);
        email = emptyToNull(email);
        function_ = emptyToNull(function_);
        service_Name = emptyToNull(service_Name);
        cost = emptyToNull(cost);
        ClientsDAO.Filter filter_1 = new ClientsDAO.Filter(client_Name, contact_person, address_client, phone_client, email_client);
        EmployeesDAO.Filter filter_2 = new EmployeesDAO.Filter(employee_Name, address, phone, email, function_);
        ServicesDAO.Filter filter_3 = null;
        if (cost == null) {
            filter_3 = new ServicesDAO.Filter(service_Name, null);
        } else {
            Float f_cost = 0.0F;
            try{
                f_cost = Float.parseFloat(cost);
            } catch (NumberFormatException e) {
                model.addAttribute("error_msg", "Некорректная стоимость");
                return "errorPage";
            }
            filter_3 = new ServicesDAO.Filter(service_Name, f_cost);
        }
        List<ServiceHistory> serviceHistory = (List<ServiceHistory>) serviceHistoryDAO.getByFilter(filter_1, filter_2, filter_3);
        model.addAttribute("searchResult", serviceHistory);
        return "serviceHistory";
    }

    /* Редактирование таблицы:
    1. переходим на страницу редактирования
    2. редактируем/удаляем на ней
    3. нажатием "Готово!" сохраняем изменения
     */

    //переходим на страницу с редактированием таблицы
    @GetMapping(value = "/serviceHistory/edit")
    public String Edit(Model model) {
        List<ServiceHistory> serviceHistory = (List<ServiceHistory>) serviceHistoryDAO.getAll();
        model.addAttribute("serviceHistory", serviceHistory);
        model.addAttribute("serviceHistoryMethods", serviceHistoryDAO);
        return "serviceHistory_edit";
    }

    //на странице редактирования таблицы удаляем запись и возвращаемся обратно на страницу редактирования
    @PostMapping("/serviceHistory/edit/delete")
    public String Delete(@RequestParam Long id,
                         Model model) {
        serviceHistoryDAO.deleteById(id);
        List<ServiceHistory> serviceHistory = (List<ServiceHistory>) serviceHistoryDAO.getAll();
        model.addAttribute("serviceHistory", serviceHistory);
        model.addAttribute("serviceHistoryMethods", serviceHistoryDAO);
        return "/serviceHistory_edit";
    }

    private String emptyToNull(String t) {
        if (t != null && t.isEmpty()) {
            return null;
        }
        return t;
    }
}
