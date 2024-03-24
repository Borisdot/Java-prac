package ru.msu.cmc.webprac.DAO;

import lombok.Getter;
import ru.msu.cmc.webprac.models.Clients;

import java.util.List;

public interface ClientsDAO extends CommonDAO<Clients, Long>{

    //to get some client/clients by filter for search
    List<Clients> getByFilter(Filter filter);

    @Getter
    class Filter {
        private final String name;
        private final String contact;
        private final String address;
        private final String phone;
        private final String email;
        public Filter(String name_, String contact_, String address_, String phone_, String email_) {
            name = name_;
            contact = contact_;
            address = address_;
            phone = phone_;
            email = email_;
        }
    }
}
