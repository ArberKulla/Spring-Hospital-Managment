package com.polis.polishospital.service.serviceImpl;

import com.polis.polishospital.dto.LongDto;
import com.polis.polishospital.dto.PatientCreateDto;
import com.polis.polishospital.dto.PatientDto;
import com.polis.polishospital.dto.StringDto;
import com.polis.polishospital.entity.Patient;
import com.polis.polishospital.repository.PatientRepository;
import com.polis.polishospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository repository;

    private PatientDto convertToDTO(Patient patient) {
        return new PatientDto(patient.getId(), patient.getName(), patient.getLastName(), patient.getBirthDate());
    }

    private Patient convertToEntity(PatientDto dto) {
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setName(dto.getName());
        patient.setLastName(dto.getLastName());
        patient.setBirthDate(dto.getBirthDate());
        return patient;
    }

    private Patient convertCreateToEntity(PatientCreateDto dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setLastName(dto.getLastName());
        patient.setBirthDate(dto.getBirthDate());
        return patient;
    }

    @Override
    public List<PatientDto> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PatientDto save(PatientCreateDto patientDTO) {
        Patient savedPatient = repository.save(convertCreateToEntity(patientDTO));
        return convertToDTO(savedPatient);
    }

    @Override
    public PatientDto getById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
        return convertToDTO(patient);
    }

    @Transactional
    @Override
    public PatientDto update(PatientDto patientDto) {
        Patient current = repository.findById(patientDto.getId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientDto.getId()));

        current.setName(patientDto.getName());
        current.setLastName(patientDto.getLastName());
        current.setBirthDate(patientDto.getBirthDate());

        Patient updatedPatient = repository.save(current);
        return convertToDTO(updatedPatient);
    }

    @Transactional
    @Override
    public void deleteById(LongDto id) {
        repository.deleteById(id.getId());
    }

    @Override
    public List<PatientDto> findByName(StringDto name) {
        List<Patient> patients = repository.findByNameIgnoreCaseContaining(name.getString());
        return patients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> findByLastName(StringDto lastName) {
        List<Patient> patients = repository.findByLastNameIgnoreCaseContaining(lastName.getString());
        return patients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> findByNameAndLastname(StringDto name, StringDto lastName) {
        List<Patient> patients = repository.findByNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining(
                name.getString(), lastName.getString());
        return patients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
