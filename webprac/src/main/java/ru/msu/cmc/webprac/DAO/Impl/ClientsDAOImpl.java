package ru.msu.cmc.webprac.DAO.Impl;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ClientsDAO;
import ru.msu.cmc.webprac.models.Clients;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientsDAOImpl extends CommonDAOImpl<Clients, Long> implements ClientsDAO {
    public ClientsDAOImpl() {
        super(Clients.class);
    }

    @Override
    public List<Clients> getByFilter(Filter filter) {
        List<Clients> ret = new ArrayList<>();
        for (Clients client: getAll()) {
            if (filter.getName() != null && !client.getName().equals(filter.getName())) {
                continue;
            }
            if (filter.getContact() != null && !client.getContact().equals(filter.getContact())) {
                continue;
            }
            if (filter.getAddress() != null && !client.getAddress().equals(filter.getAddress())) {
                continue;
            }
            if (filter.getPhone() != null && !client.getPhone().equals(filter.getPhone())) {
                continue;
            }
            if (filter.getEmail() != null && !client.getEmail().equals(filter.getEmail())) {
                continue;
            }
            ret.add(client);
        }
        return ret;
    }
}
