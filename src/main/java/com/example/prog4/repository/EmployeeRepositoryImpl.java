package com.example.prog4.repository;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.cnaps.CnapsEmployeeRepository;
import com.example.prog4.repository.cnaps.entity.CnapsEmployee;
import com.example.prog4.repository.main.MainEmployeeRepository;
import com.example.prog4.repository.main.dao.EmployeeManagerDao;
import com.example.prog4.repository.main.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private MainEmployeeRepository mainEmployeeRepository;
    private CnapsEmployeeRepository cnapsEmployeeRepository;
    private EmployeeManagerDao employeeManagerDao;
    @Override
    public Employee getEmployee(String id) {
        Employee employee = mainEmployeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id=" + id));
        if(employee != null){
            String cnaps = cnapsEmployeeRepository.findByEndToEndId(id).map((e) -> e.getCnaps()).orElse(null);
            employee.setCnaps(cnaps);
            return employee;
        }return null;
    }
    @Override
    public List<Employee> getEmployees(EmployeeFilter filter) {
        Sort sort = Sort.by(filter.getOrderDirection(), filter.getOrderBy().toString());
        Pageable pageable = PageRequest.of(filter.getIntPage() - 1, filter.getIntPerPage(), sort);
        List<Employee> employees = employeeManagerDao.findByCriteria(
                filter.getLastName(),
                filter.getFirstName(),
                filter.getCountryCode(),
                filter.getSex(),
                filter.getPosition(),
                filter.getEntrance(),
                filter.getDeparture(),
                pageable
        );
        employees.forEach((employee) -> {
            String cnaps = cnapsEmployeeRepository.findByEndToEndId(employee.getId()).map((e) -> e.getCnaps()).orElse(null);
            employee.setCnaps(cnaps);
        });
        return employees;
    }
}
