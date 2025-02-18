package com.polis.polishospital.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.polis.polishospital.entity.AdmissionState;
import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.DischargeReason;
import com.polis.polishospital.repository.AdmissionStateRepository;
import com.polis.polishospital.repository.DepartmentRepository;
import com.polis.polishospital.service.serviceImpl.AdmissionStateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AdmissionStateServiceTest {

    @Mock
    private AdmissionStateRepository admissionStateRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private AdmissionStateServiceImpl admissionStateService;

    private AdmissionState admissionState;
    private Department department;

    @BeforeEach
    void setUp() {
        admissionState = new AdmissionState();
        admissionState.setId(1L);
        admissionState.setPatient(null);  // You can set up the patient details here
        admissionState.setDepartment(null);
        admissionState.setEnteringDate(LocalDateTime.now());
        admissionState.setDischarge(false);

        department = new Department();
        department.setId(1L);
        department.setName("Cardiology");
    }

    @Test
    void testGetAll() {
        when(admissionStateRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(Arrays.asList(admissionState));

        var result = admissionStateService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(admissionStateRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void testSave() {
        when(admissionStateRepository.save(any(AdmissionState.class))).thenReturn(admissionState);

        AdmissionState saved = admissionStateService.save(admissionState);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        verify(admissionStateRepository, times(1)).save(any(AdmissionState.class));
    }

    @Test
    void testGetById() {
        when(admissionStateRepository.findById(1L)).thenReturn(Optional.of(admissionState));

        AdmissionState found = admissionStateService.getById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        verify(admissionStateRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        when(admissionStateRepository.findById(1L)).thenReturn(Optional.of(admissionState));
        when(admissionStateRepository.save(any(AdmissionState.class))).thenReturn(admissionState);

        AdmissionState updated = new AdmissionState();
        updated.setCause("Updated cause");

        AdmissionState result = admissionStateService.update(1L, updated);

        assertNotNull(result);
        assertEquals("Updated cause", result.getCause());
        verify(admissionStateRepository, times(1)).save(any(AdmissionState.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(admissionStateRepository).deleteById(1L);

        admissionStateService.deleteById(1L);

        verify(admissionStateRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetByPatientId() {
        when(admissionStateRepository.findByPatient_Id(1L)).thenReturn(Arrays.asList(admissionState));

        var result = admissionStateService.getByPatientId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(admissionStateRepository, times(1)).findByPatient_Id(1L);
    }

    @Test
    void testHasActiveAdmissionState() {
        when(admissionStateRepository.existsByPatient_IdAndDischargeFalse(1L)).thenReturn(true);

        boolean hasActive = admissionStateService.hasActiveAdmissionState(1L);

        assertTrue(hasActive);
        verify(admissionStateRepository, times(1)).existsByPatient_IdAndDischargeFalse(1L);
    }

    @Test
    void testUpdateDepartment() {
        when(admissionStateRepository.findById(1L)).thenReturn(Optional.of(admissionState));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(admissionStateRepository.save(any(AdmissionState.class))).thenReturn(admissionState);

        AdmissionState updated = admissionStateService.updateDepartment(1L, 1L);

        assertNotNull(updated);
        assertEquals(department, updated.getDepartment());
        verify(admissionStateRepository, times(1)).save(any(AdmissionState.class));
    }

    @Test
    void testDischargePatient() {
        when(admissionStateRepository.findById(1L)).thenReturn(Optional.of(admissionState));
        when(admissionStateRepository.save(any(AdmissionState.class))).thenReturn(admissionState);

        AdmissionState dischargeData = new AdmissionState();
        dischargeData.setReason("Recovered");
        dischargeData.setDischargeReason(DischargeReason.DECEASED);

        AdmissionState result = admissionStateService.dischargePatient(1L, dischargeData);

        assertNotNull(result);
        assertTrue(result.getDischarge());
        assertEquals("Recovered", result.getReason());
        assertEquals(DischargeReason.DECEASED,result.getDischargeReason());
        verify(admissionStateRepository, times(1)).save(any(AdmissionState.class));
    }
}
