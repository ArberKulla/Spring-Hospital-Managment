package com.polis.polishospital.controller;

import com.polis.polishospital.entity.Department;
import com.polis.polishospital.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/departments")
public class DepartmentRESTController {

    @Autowired
    private DepartmentService service;


    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        List<Department> departments = service.getAll();
        return new ResponseEntity<>(departments, HttpStatus.OK); // HTTP 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable long id) {
        Department department = service.getById(id);
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        Department createdDepartment = service.save(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable long id, @RequestBody Department department) {
        Department updatedDepartment = service.update(id, department);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchByCode/{code}")
    public List<Department> searchDepartmentsByCode(@PathVariable String code) {
        return service.findByCode(code);
    }

    @GetMapping("/searchByName/{name}")
    public List<Department> searchDepartmentsByName(@PathVariable String name) {
        return service.findByName(name);
    }

}
