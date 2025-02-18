package com.polis.polishospital.service.serviceImpl;

import com.polis.polishospital.entity.*;
import com.polis.polishospital.repository.AdmissionStateRepository;
import com.polis.polishospital.repository.ClinicalDataRepository;
import com.polis.polishospital.repository.DepartmentRepository;
import com.polis.polishospital.service.AdmissionStateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdmissionStateServiceImpl implements AdmissionStateService {

    @Autowired
    AdmissionStateRepository admissionStateRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<AdmissionState> getAll() {
        return admissionStateRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional
    @Override
    public AdmissionState save(AdmissionState admissionState) {
        return admissionStateRepository.save(admissionState);
    }

    @Override
    public AdmissionState getById(long id) {
        return admissionStateRepository.findById(id).get();
    }

    @Transactional
    @Override
    public AdmissionState update(long id, AdmissionState admissionState) {
        AdmissionState current = admissionStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AdmissionState not found with ID: " + id));

        current.setPatient(admissionState.getPatient());
        current.setDepartment(admissionState.getDepartment());
        current.setEnteringDate(admissionState.getEnteringDate());
        current.setExitingDate(admissionState.getExitingDate());
        current.setCause(admissionState.getCause());
        current.setDischarge(admissionState.getDischarge());
        current.setDischargeReason(admissionState.getDischargeReason());

        return admissionStateRepository.save(current);
    }

    @Transactional
    @Override
    public void deleteById(long id){
        admissionStateRepository.deleteById(id);
    }

    @Override
    public List<AdmissionState> getByPatientId(long patientId) {
        return admissionStateRepository.findByPatient_Id(patientId);
    }

    @Override
    public boolean hasActiveAdmissionState(long patientId) {
        return admissionStateRepository.existsByPatient_IdAndDischargeFalse(patientId);
    }

    @Transactional
    @Override
    public AdmissionState updateDepartment(long id, long departmentId) {
        AdmissionState current = admissionStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AdmissionState not found with ID: " + id));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("DepartmentId not found with ID: " + departmentId));

        current.setDepartment(department);

        return admissionStateRepository.save(current);
    }

    @Transactional
    @Override
    public AdmissionState dischargePatient(long id, AdmissionState admissionState) {
        AdmissionState current = admissionStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AdmissionState not found with ID: " + id));
        current.setDischarge(true);
        current.setExitingDate(LocalDateTime.now());
        current.setReason(admissionState.getReason());
        current.setDischargeReason(admissionState.getDischargeReason());

        return admissionStateRepository.save(current);
    }
}
