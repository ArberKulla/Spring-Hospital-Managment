package com.polis.polishospital.service;

import com.polis.polishospital.entity.AdmissionState;
import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.entity.DischargeReason;

import java.util.List;

public interface AdmissionStateService {

    List<AdmissionState> getAll();

    AdmissionState save(AdmissionState admissionState);

    AdmissionState getById(long id);

    AdmissionState update(long id, AdmissionState admissionState);

    void deleteById(long id);

    List<AdmissionState> getByPatientId(long patientId);

    boolean hasActiveAdmissionState(long patientId);

    AdmissionState updateDepartment(long id, long department_id);

    AdmissionState dischargePatient(long id, AdmissionState admissionState);
}
