package ru.msu.cmc.webprac.DAO;

import lombok.Getter;
import ru.msu.cmc.webprac.models.Services;

import java.util.List;

public interface ServicesDAO extends CommonDAO<Services, Long>{
    //to get some service/services by filter for search
    List<Services> getByFilter(Filter filter);

    @Getter
    class Filter {
        private final String name;
        private final Float cost;
        public Filter(String name_, Float cost_) {
            name = name_;
            cost = cost_;
        }
    }
}
