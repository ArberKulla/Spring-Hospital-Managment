package com.polis.polishospital.service;

import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.entity.Department;

import java.util.List;

public interface ClinicalDataService {

    List<ClinicalData> getAll();

    List<ClinicalData> getByPatientId(Long patientId);

    List<ClinicalData> getByPatientIdAndRecord(Long patientId, String clinicalRecord);

    ClinicalData save(ClinicalData clinicalData);

    ClinicalData getById(long id);

    ClinicalData update(long id, ClinicalData clinicalData);

    void deleteById(long id);

}
