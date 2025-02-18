package com.polis.polishospital.service.serviceImpl;

import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.Patient;
import com.polis.polishospital.repository.DepartmentRepository;
import com.polis.polishospital.repository.PatientRepository;
import com.polis.polishospital.service.DepartmentService;
import com.polis.polishospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository repository;

    @Override
    public List<Department> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional
    @Override
    public Department save(Department department) {
        return repository.save(department);
    }

    @Override
    public Department getById(long id) {
        return repository.findById(id).get();
    }

    @Transactional
    @Override
    public Department update(long id, Department department) {
        Department current = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));

        current.setName(department.getName());
        current.setCode(department.getCode());

        return repository.save(current);
    }

    @Transactional
    @Override
    public void deleteById(long id){
        repository.deleteById(id);
    }

    @Override
    public List<Department> findByCode(String code) {
        return repository.findByCodeIgnoreCaseContaining(code);

    }

    @Override
    public List<Department> findByName(String name) {
        return repository.findByNameIgnoreCaseContaining(name);
    }

}
