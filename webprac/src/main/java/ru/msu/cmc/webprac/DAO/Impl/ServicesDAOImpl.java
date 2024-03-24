package ru.msu.cmc.webprac.DAO.Impl;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.ServicesDAO;
import ru.msu.cmc.webprac.models.Services;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicesDAOImpl extends CommonDAOImpl<Services, Long> implements ServicesDAO {
    public ServicesDAOImpl() {
        super(Services.class);
    }

    @Override
    public List<Services> getByFilter(Filter filter) {
        List<Services> ret = new ArrayList<>();
        for (Services service: getAll()) {
            if (filter.getName() != null && !service.getName().equals(filter.getName())){
                continue;
            }
            if (filter.getCost() != null && !service.getCost().equals(filter.getCost())) {
                continue;
            }
            ret.add(service);
        }
        return ret;
    }
}
