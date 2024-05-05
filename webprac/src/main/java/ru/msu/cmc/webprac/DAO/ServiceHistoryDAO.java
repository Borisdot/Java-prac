package ru.msu.cmc.webprac.DAO;

import ru.msu.cmc.webprac.models.ServiceHistory;

import java.util.Collection;

public interface ServiceHistoryDAO extends CommonDAO<ServiceHistory, Long>{
    //to get some history by filter for search
    Collection<ServiceHistory> getByFilter(ClientsDAO.Filter filter1,
                                           EmployeesDAO.Filter filter2,
                                           ServicesDAO.Filter filter3);

    Boolean ifThereIsSuchItem(ServiceHistory serviceHistory);
}
