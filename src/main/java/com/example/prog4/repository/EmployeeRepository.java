package com.example.prog4.repository;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.repository.main.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EmployeeRepository{
    Employee getEmployee(String id);
    List<Employee> getEmployees(EmployeeFilter filter);
}
