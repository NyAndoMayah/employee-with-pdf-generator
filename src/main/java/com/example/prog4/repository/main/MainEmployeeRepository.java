package com.example.prog4.repository.main;

import com.example.prog4.repository.main.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainEmployeeRepository extends JpaRepository<Employee, String> {
}
