package com.polis.polishospital.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.polis.polishospital.dto.LongDto;
import com.polis.polishospital.dto.PatientCreateDto;
import com.polis.polishospital.dto.PatientDto;
import com.polis.polishospital.dto.StringDto;
import com.polis.polishospital.entity.Patient;
import com.polis.polishospital.repository.PatientRepository;
import com.polis.polishospital.service.serviceImpl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientDto patientDto;
    private PatientCreateDto patientCreateDto;
    private Patient patient;

    @BeforeEach
    void setUp() {
        // Prepare DTO and Entity
        patient = new Patient();
        patient.setId(1L);
        patient.setName("John");
        patient.setLastName("Doe");
        patient.setBirthDate(LocalDate.now());

        patientDto = new PatientDto(patient.getId(), patient.getName(), patient.getLastName(), patient.getBirthDate());
        patientCreateDto = new PatientCreateDto( patient.getName(), patient.getLastName(), patient.getBirthDate());
    }

    @Test
    void testGetAll() {
        when(patientRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(Arrays.asList(patient));

        var result = patientService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(patientDto.getName(), result.get(0).getName());
        verify(patientRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void testSave() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientDto savedPatientDto = patientService.save(patientCreateDto);

        assertNotNull(savedPatientDto);
        assertEquals("John", savedPatientDto.getName());
        assertEquals("Doe", savedPatientDto.getLastName());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testGetById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        PatientDto foundPatientDto = patientService.getById(1L);

        assertNotNull(foundPatientDto);
        assertEquals(1L, foundPatientDto.getId());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        patientDto.setName("Updated Name");
        patientDto.setLastName("Updated LastName");

        PatientDto updatedPatientDto = patientService.update(patientDto);

        assertNotNull(updatedPatientDto);
        assertEquals("Updated Name", updatedPatientDto.getName());
        assertEquals("Updated LastName", updatedPatientDto.getLastName());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(patientRepository).deleteById(1L);

        patientService.deleteById(new LongDto(1L));

        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByName() {
        when(patientRepository.findByNameIgnoreCaseContaining("John")).thenReturn(Arrays.asList(patient));

        var result = patientService.findByName(new StringDto("John"));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(patientRepository, times(1)).findByNameIgnoreCaseContaining("John");
    }

    @Test
    void testFindByLastName() {
        when(patientRepository.findByLastNameIgnoreCaseContaining("Doe")).thenReturn(Arrays.asList(patient));

        var result = patientService.findByLastName(new StringDto("Doe"));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(patientRepository, times(1)).findByLastNameIgnoreCaseContaining("Doe");
    }

    @Test
    void testFindByNameAndLastname() {
        when(patientRepository.findByNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining("John", "Doe"))
                .thenReturn(Arrays.asList(patient));

        var result = patientService.findByNameAndLastname(new StringDto("John"), new StringDto("Doe"));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(patientRepository, times(1))
                .findByNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining("John", "Doe");
    }
}
