package ru.msu.cmc.webprac.DAO.Impl;

import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac.DAO.EmployeesDAO;
import ru.msu.cmc.webprac.models.Employees;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeesDAOImpl extends CommonDAOImpl<Employees, Long> implements EmployeesDAO {
    public EmployeesDAOImpl() {
        super(Employees.class);
    }

    @Override
    public List<Employees> getByFilter(Filter filter) {
        List<Employees> ret = new ArrayList<>();
        for (Employees employee: getAll()) {
            if (filter.getName() != null && !employee.getName().equals(filter.getName())){
                continue;
            }
            if (filter.getAddress() != null && !employee.getAddress().equals(filter.getAddress())){
                continue;
            }
            if (filter.getPhone() != null && !employee.getPhone().equals(filter.getPhone())){
                continue;
            }
            if (filter.getEmail() != null && !employee.getEmail().equals(filter.getEmail())){
                continue;
            }
            if (filter.getFunction_() != null && !employee.getFunction_().equals(filter.getFunction_())){
                continue;
            }
            ret.add(employee);
        }
        return ret;
    }
}
