package com.polis.polishospital.repository;

import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Long> {

    List<ClinicalData> findByPatient_Id(Long patientId);

    List<ClinicalData> findByPatient_IdAndClinicalRecordContainingIgnoreCase(Long patientId, String clinicalRecord);

}
