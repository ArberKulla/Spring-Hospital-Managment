package com.polis.polishospital.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.repository.ClinicalDataRepository;
import com.polis.polishospital.service.serviceImpl.ClinicalDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClinicalDataServiceTest {

    @Mock
    private ClinicalDataRepository clinicalDataRepository;

    @InjectMocks
    private ClinicalDataServiceImpl clinicalDataService;

    private ClinicalData clinicalData;

    @BeforeEach
    void setUp() {
        clinicalData = new ClinicalData();
        clinicalData.setId(1L);
        clinicalData.setClinicalRecord("Sample Record");
    }

    @Test
    void testGetAll() {
        when(clinicalDataRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(Arrays.asList(clinicalData));

        List<ClinicalData> result = clinicalDataService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clinicalDataRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void testGetByPatientId() {
        when(clinicalDataRepository.findByPatient_Id(1L)).thenReturn(Arrays.asList(clinicalData));

        List<ClinicalData> result = clinicalDataService.getByPatientId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clinicalDataRepository, times(1)).findByPatient_Id(1L);
    }

    @Test
    void testSave() {
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        ClinicalData savedData = clinicalDataService.save(clinicalData);

        assertNotNull(savedData);
        assertEquals("Sample Record", savedData.getClinicalRecord());
        verify(clinicalDataRepository, times(1)).save(any(ClinicalData.class));
    }

    @Test
    void testUpdate() {
        when(clinicalDataRepository.findById(1L)).thenReturn(Optional.of(clinicalData));
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        ClinicalData updatedData = new ClinicalData();
        updatedData.setClinicalRecord("Updated Record");

        ClinicalData result = clinicalDataService.update(1L, updatedData);

        assertNotNull(result);
        assertEquals("Updated Record", result.getClinicalRecord());
        verify(clinicalDataRepository, times(1)).save(clinicalData);
    }

    @Test
    void testDeleteById() {
        doNothing().when(clinicalDataRepository).deleteById(1L);

        clinicalDataService.deleteById(1L);

        verify(clinicalDataRepository, times(1)).deleteById(1L);
    }
}
