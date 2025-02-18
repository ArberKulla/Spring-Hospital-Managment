package com.polis.polishospital.service;

import com.polis.polishospital.dto.LongDto;
import com.polis.polishospital.dto.PatientCreateDto;
import com.polis.polishospital.dto.PatientDto;
import com.polis.polishospital.dto.StringDto;
import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.Patient;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAll();

    PatientDto save(PatientCreateDto patientDTO);

    PatientDto getById(Long id);

    PatientDto update(PatientDto patientDTO);

    void deleteById(LongDto id);

    List<PatientDto> findByName(StringDto name);

    List<PatientDto> findByLastName(StringDto lastName);

    List<PatientDto> findByNameAndLastname(StringDto name, StringDto lastName);
}
