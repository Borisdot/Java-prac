package ru.msu.cmc.webprac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac.DAO.EmployeesDAO;
import ru.msu.cmc.webprac.DAO.Impl.EmployeesDAOImpl;
import ru.msu.cmc.webprac.DAO.Impl.ServicesDAOImpl;
import ru.msu.cmc.webprac.DAO.ServicesDAO;
import ru.msu.cmc.webprac.models.Employees;
import ru.msu.cmc.webprac.models.Services;

import java.util.List;
import java.util.Map;

@Controller
public class ServicesController {
    @Autowired
    private final ServicesDAO servicesDAO = new ServicesDAOImpl();

    @PostMapping(value = "/services/add")
    public String Add( @RequestParam String service_Name,
                       @RequestParam String cost,
                       Model model) {
        service_Name = emptyToNull(service_Name);
        cost = emptyToNull(cost);
        if (service_Name == null) {
            model.addAttribute("error_msg", "Не введено название услуги");
            return "errorPage";
        }
        if (cost == null) {
            model.addAttribute("error_msg", "Не введена стоимость");
            return "errorPage";
        }
        Float f_cost = 0.0F;
        try{
            f_cost = Float.parseFloat(cost);
        } catch (NumberFormatException e) {
            model.addAttribute("error_msg", "Некорректная стоимость");
            return "errorPage";
        }
        Services services = new Services(service_Name, f_cost);
        ServicesDAO.Filter filter = new ServicesDAO.Filter(service_Name, f_cost);
        if (!servicesDAO.getByFilter(filter).isEmpty()) {
            model.addAttribute("error_msg", "Уже есть такая услуга!!!");
            return "errorPage";
        }
        servicesDAO.save(services);
        return "redirect:../services";
    }

    @GetMapping(value = "/services/search")
    public String Search(@RequestParam(required = false) String service_Name,
                         @RequestParam(required = false) String cost,
                         Model model) {
        service_Name = emptyToNull(service_Name);
        cost = emptyToNull(cost);
        ServicesDAO.Filter filter;
        if (cost == null) {
            filter = new ServicesDAO.Filter(service_Name, null);
        } else {
            Float f_cost = 0.0F;
            try{
                f_cost = Float.parseFloat(cost);
            } catch (NumberFormatException e) {
                model.addAttribute("error_msg", "Некорректная стоимость");
                return "errorPage";
            }
            filter = new ServicesDAO.Filter(service_Name, f_cost);
        }
        List<Services> services = servicesDAO.getByFilter(filter);
        model.addAttribute("searchResult", services);
        return "services";
    }

    /* Редактирование таблицы:
    1. переходим на страницу редактирования
    2. редактируем/удаляем на ней
    3. нажатием "Готово!" сохраняем изменения
     */

    //переходим на страницу с редактированием таблицы
    @GetMapping(value = "/services/edit")
    public String Edit(Model model) {
        List<Services> services = (List<Services>) servicesDAO.getAll();
        model.addAttribute("services", services);
        model.addAttribute("servicesMethods", servicesDAO);
        return "services_edit";
    }

    //на странице редактирования таблицы удаляем запись и возвращаемся обратно на страницу редактирования
    @PostMapping("/services/edit/delete")
    public String Delete(@RequestParam Long id,
                         Model model) {
        servicesDAO.deleteById(id);
        List<Services> services = (List<Services>) servicesDAO.getAll();
        model.addAttribute("services", services);
        model.addAttribute("servicesMethods", servicesDAO);
        return "/services_edit";
    }

    @PostMapping("/update-service-info")
    public ResponseEntity<String> updateClientInfo(@RequestBody Map<String, String> data) {
        // Получение данных из запроса
        String id = data.get("id");
        String name = data.get("param1");
        String cost = data.get("param2");
        Services temp = new Services(Long.valueOf(id), name, Float.parseFloat(cost));
        // Обновление информации об услуге в базе данных
        servicesDAO.update(temp);
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
