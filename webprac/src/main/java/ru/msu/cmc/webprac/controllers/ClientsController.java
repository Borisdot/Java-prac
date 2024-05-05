package ru.msu.cmc.webprac.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprac.DAO.ClientsDAO;
import ru.msu.cmc.webprac.models.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import ru.msu.cmc.webprac.DAO.Impl.ClientsDAOImpl;

import java.util.List;
import java.util.Map;

@Controller
public class ClientsController {
    @Autowired
    private final ClientsDAO clientsDAO = new ClientsDAOImpl();

    @PostMapping(value = "/clients/add")
    public String Add( @RequestParam String client_Name,
                       @RequestParam String contact_person,
                       @RequestParam String address,
                       @RequestParam String phone,
                       @RequestParam String email,
                       Model model) {
        client_Name = emptyToNull(client_Name);
        contact_person = emptyToNull(contact_person);
        address = emptyToNull(address);
        phone = emptyToNull(phone);
        email = emptyToNull(email);
        if (client_Name == null) {
            model.addAttribute("error_msg", "Не введено имя");
            return "errorPage";
        }
        if (contact_person == null) {
            model.addAttribute("error_msg", "Не введен контактный человек");
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

        Clients clients = new Clients(client_Name, contact_person, address, phone, email);
        ClientsDAO.Filter filter = new ClientsDAO.Filter(client_Name, contact_person, address, phone, email);
        if (!clientsDAO.getByFilter(filter).isEmpty()) {
            model.addAttribute("error_msg", "Уже есть такой человек!!!");
            return "errorPage";
        }
        clientsDAO.save(clients);
        return "redirect:../clients";
    }

    @GetMapping(value = "/clients/search")
    public String Search(@RequestParam(required = false) String client_Name,
                         @RequestParam(required = false) String contact_person,
                         @RequestParam(required = false) String address,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String email,
                         Model model) {
        client_Name = emptyToNull(client_Name);
        contact_person = emptyToNull(contact_person);
        address = emptyToNull(address);
        phone = emptyToNull(phone);
        email = emptyToNull(email);

        ClientsDAO.Filter filter = new ClientsDAO.Filter(client_Name, contact_person, address, phone, email);
        List<Clients> clients = clientsDAO.getByFilter(filter);
        model.addAttribute("searchResult", clients);
        return "clients";
    }

    /* Редактирование таблицы:
    1. переходим на страницу редактирования
    2. редактируем/удаляем на ней
    3. нажатием "Готово!" сохраняем изменения
     */

    //переходим на страницу с редактированием таблицы
    @GetMapping(value = "/clients/edit")
    public String Edit(Model model) {
        List<Clients> clients = (List<Clients>) clientsDAO.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("clientsMethods", clientsDAO);
        return "clients_edit";
    }

    //на странице редактирования таблицы удаляем запись и возвращаемся обратно на страницу редактирования
    @PostMapping("/clients/edit/delete")
    public String Delete(@RequestParam Long id,
                         Model model) {
        clientsDAO.deleteById(id);
        List<Clients> clients = (List<Clients>) clientsDAO.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("clientsMethods", clientsDAO);
        return "/clients_edit";
    }

    @PostMapping("/update-client-info")
    public ResponseEntity<String> updateClientInfo(@RequestBody Map<String, String> data) {
        // Получение данных из запроса
        String id = data.get("id");
        String name = data.get("param1");
        String contact = data.get("param2");
        String adress = data.get("param3");
        String phone = data.get("param4");
        String email = data.get("param5");
        Clients temp = new Clients(Long.valueOf(id), name, contact, adress, phone, email);
        // Обновление информации о клиенте в базе данных
        clientsDAO.update(temp);
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
