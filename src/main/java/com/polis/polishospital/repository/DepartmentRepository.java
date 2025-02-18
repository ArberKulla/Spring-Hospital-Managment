package com.polis.polishospital.repository;

import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByCodeIgnoreCaseContaining(String code);

    List<Department> findByNameIgnoreCaseContaining(String name);
}