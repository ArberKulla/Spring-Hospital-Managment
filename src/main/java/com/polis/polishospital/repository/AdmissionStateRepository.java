package com.polis.polishospital.repository;

import com.polis.polishospital.entity.AdmissionState;
import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionStateRepository extends JpaRepository<AdmissionState, Long> {

    List<AdmissionState> findByPatient_Id(long patient_id);

    boolean existsByPatient_IdAndDischargeFalse(long patientId);
}
