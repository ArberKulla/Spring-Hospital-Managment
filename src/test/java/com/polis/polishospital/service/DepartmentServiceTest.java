package com.polis.polishospital.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.polis.polishospital.entity.Department;
import com.polis.polishospital.repository.DepartmentRepository;
import com.polis.polishospital.service.serviceImpl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Cardiology");
        department.setCode("C01");
    }

    //Failed Test
    @Test
    void testUpdateWithBug() {
        Department originalDepartment = new Department();
        originalDepartment.setId(1L);
        originalDepartment.setName("Cardiology");
        originalDepartment.setCode("C01");

        Department updatedDepartment = new Department();
        updatedDepartment.setName("Neurology");
        updatedDepartment.setCode("N01");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(originalDepartment));
        when(departmentRepository.save(any(Department.class))).thenAnswer(invocation -> {
            Department department = invocation.getArgument(0);
            department.setName("Cardiology");
            return department;
        });

        Department result = departmentService.update(1L, updatedDepartment);

        assertNotEquals("Cardiology", result.getName(), "The department name should have been updated!");
        assertEquals("Neurology", result.getName(), "The department name should be 'Neurology'.");
    }


    @Test
    void testGetAll() {
        when(departmentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(Arrays.asList(department));

        var result = departmentService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departmentRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void testSave() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department savedDepartment = departmentService.save(department);

        assertNotNull(savedDepartment);
        assertEquals("Cardiology", savedDepartment.getName());
        assertEquals("C01", savedDepartment.getCode());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testGetById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentService.getById(1L);

        assertNotNull(result);
        assertEquals("Cardiology", result.getName());
        assertEquals("C01", result.getCode());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        Department updatedDepartment = new Department();
        updatedDepartment.setName("Neurology");
        updatedDepartment.setCode("N01");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

        Department result = departmentService.update(1L, updatedDepartment);

        assertNotNull(result);
        assertEquals("Neurology", result.getName());
        assertEquals("N01", result.getCode());
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(departmentRepository).deleteById(1L);

        departmentService.deleteById(1L);

        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByCode() {
        when(departmentRepository.findByCodeIgnoreCaseContaining("C01")).thenReturn(Arrays.asList(department));

        var result = departmentService.findByCode("C01");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cardiology", result.get(0).getName());
        verify(departmentRepository, times(1)).findByCodeIgnoreCaseContaining("C01");
    }

    @Test
    void testFindByName() {
        when(departmentRepository.findByNameIgnoreCaseContaining("Cardiology")).thenReturn(Arrays.asList(department));

        var result = departmentService.findByName("Cardiology");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("C01", result.get(0).getCode());
        verify(departmentRepository, times(1)).findByNameIgnoreCaseContaining("Cardiology");
    }
}
