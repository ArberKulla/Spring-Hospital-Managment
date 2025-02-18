package com.polis.polishospital.service;

import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.Patient;

import java.util.List;

public interface DepartmentService {

    List<Department> getAll();

    Department save(Department department);

    Department getById(long id);

    Department update(long id, Department department);

    void deleteById(long id);

    List<Department> findByCode(String code);

    List<Department> findByName(String name);
}
