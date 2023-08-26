package com.example.prog4.service;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.EmployeeRepositoryImpl;
import com.example.prog4.repository.main.MainEmployeeRepository;
import com.example.prog4.repository.main.dao.EmployeeManagerDao;
import com.example.prog4.repository.main.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private MainEmployeeRepository repository;
    private EmployeeManagerDao employeeManagerDao;
    private EmployeeRepositoryImpl employeeRepository;


    public Employee getOne(String id) {
        return employeeRepository.getEmployee(id);
    }

    public List<Employee> getAll(EmployeeFilter filter) {
        return employeeRepository.getEmployees(filter);
    }

    public void saveOne(Employee employee) {
        repository.save(employee);
    }
}
