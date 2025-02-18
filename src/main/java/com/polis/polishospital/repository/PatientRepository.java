package com.polis.polishospital.repository;

import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository  extends JpaRepository<Patient, Long> {

    List<Patient> findByNameIgnoreCaseContaining(String name);

    List<Patient> findByLastNameIgnoreCaseContaining(String lastName);

    List<Patient> findByNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining(String name, String lastName);
}
