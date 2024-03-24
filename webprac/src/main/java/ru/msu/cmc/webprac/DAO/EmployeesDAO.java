package ru.msu.cmc.webprac.DAO;

import lombok.Getter;
import ru.msu.cmc.webprac.models.Employees;

import java.util.List;

public interface EmployeesDAO extends CommonDAO<Employees, Long>{

    //to get some employee/employees by filter for search
    List<Employees> getByFilter(Filter filter);

    @Getter
    class Filter {
        private final String name;
        private final String address;
        private final String phone;
        private final String email;
        private final String function_;
        public Filter(String name_, String address_, String phone_, String email_, String function__) {
            name = name_;
            address = address_;
            phone = phone_;
            email = email_;
            function_ = function__;
        }
    }
}
