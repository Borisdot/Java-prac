package ru.msu.cmc.webprac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac.DAO.ClientsDAO;
import ru.msu.cmc.webprac.DAO.EmployeesDAO;
import ru.msu.cmc.webprac.DAO.Impl.EmployeesDAOImpl;
import ru.msu.cmc.webprac.models.Clients;
import ru.msu.cmc.webprac.models.Employees;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeesController {
    @Autowired
    private final EmployeesDAO employeesDAO = new EmployeesDAOImpl();

    @PostMapping(value = "/employees/add")
    public String Add( @RequestParam String employee_Name,
                       @RequestParam String address,
                       @RequestParam String phone,
                       @RequestParam String email,
                       @RequestParam String function_,
                       Model model) {
        employee_Name = emptyToNull(employee_Name);
        address = emptyToNull(address);
        phone = emptyToNull(phone);
        email = emptyToNull(email);
        function_ = emptyToNull(function_);
        if (employee_Name == null) {
            model.addAttribute("error_msg", "Не введено имя");
            return "errorPage";
        }
        if (address == null) {
            model.addAttribute("error_msg", "Не введен адресс");
            return "errorPage";
        }
        if (phone == null) {
            model.addAttribute("error_msg", "Не введен телефон");
            return "errorPage";
        }
        if (email == null) {
            model.addAttribute("error_msg", "Не введена почта");
            return "errorPage";
        }
        if (function_ == null) {
            model.addAttribute("error_msg", "Не введена должность");
            return "errorPage";
        }

        Employees employees = new Employees(employee_Name, address, phone, email, function_);
        EmployeesDAO.Filter filter = new EmployeesDAO.Filter(employee_Name, address, phone, email, function_);
        if (!employeesDAO.getByFilter(filter).isEmpty()) {
            model.addAttribute("error_msg", "Уже есть такой человек!!!");
            return "errorPage";
        }
        employeesDAO.save(employees);
        return "redirect:../employees";
    }

    @GetMapping(value = "/employees/search")
    public String Search(@RequestParam(required = false) String employee_Name,
                         @RequestParam(required = false) String address,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String function_,
                         Model model) {
        employee_Name = emptyToNull(employee_Name);
        address = emptyToNull(address);
        phone = emptyToNull(phone);
        email = emptyToNull(email);
        function_ = emptyToNull(function_);

        EmployeesDAO.Filter filter = new EmployeesDAO.Filter(employee_Name, address, phone, email, function_);
        List<Employees> employees = employeesDAO.getByFilter(filter);
        model.addAttribute("searchResult", employees);
        return "employees";
    }


    /* Редактирование таблицы:
    1. переходим на страницу редактирования
    2. редактируем/удаляем на ней
    3. нажатием "Готово!" сохраняем изменения
     */

    //переходим на страницу с редактированием таблицы
    @GetMapping(value = "/employees/edit")
    public String Edit(Model model) {
        List<Employees> employees = (List<Employees>) employeesDAO.getAll();
        model.addAttribute("employees", employees);
        model.addAttribute("employeesMethods", employeesDAO);
        return "employees_edit";
    }

    //на странице редактирования таблицы удаляем запись и возвращаемся обратно на страницу редактирования
    @PostMapping("/employees/edit/delete")
    public String Delete(@RequestParam Long id,
                         Model model) {
        employeesDAO.deleteById(id);
        List<Employees> employees = (List<Employees>) employeesDAO.getAll();
        model.addAttribute("employees", employees);
        model.addAttribute("employeesMethods", employeesDAO);
        return "/employees_edit";
    }

    @PostMapping("/update-employee-info")
    public ResponseEntity<String> updateClientInfo(@RequestBody Map<String, String> data) {
        // Получение данных из запроса
        String id = data.get("id");
        String name = data.get("param1");
        String adress = data.get("param2");
        String phone = data.get("param3");
        String email = data.get("param4");
        String function_ = data.get("param5");
        Employees temp = new Employees(Long.valueOf(id), name, adress, phone, email, function_);
        // Обновление информации о служащем в базе данных
        employeesDAO.update(temp);
        // Возврат ответа клиенту
        return ResponseEntity.ok("Данные успешно обновлены");
    }

    private String emptyToNull(String t) {
        if (t != null && t.isEmpty()) {
            return null;
        }
        return t;
    }
}
