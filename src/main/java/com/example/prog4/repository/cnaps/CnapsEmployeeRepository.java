package com.example.prog4.repository.cnaps;

import com.example.prog4.repository.cnaps.entity.CnapsEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "cnapsEmployeeRepository")
public interface CnapsEmployeeRepository extends JpaRepository<CnapsEmployee, String> {


    Optional<CnapsEmployee> findByEndToEndId(String endToEndId);
}
