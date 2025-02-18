package com.polis.polishospital.service.serviceImpl;

import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.repository.ClinicalDataRepository;
import com.polis.polishospital.repository.ClinicalDataRepository;
import com.polis.polishospital.service.ClinicalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClinicalDataServiceImpl implements ClinicalDataService {

    @Autowired
    ClinicalDataRepository repository;

    @Override
    public List<ClinicalData> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<ClinicalData> getByPatientId(Long patientId){
        return repository.findByPatient_Id(patientId);
    }

    @Override
    public List<ClinicalData> getByPatientIdAndRecord(Long patientId, String clinicalRecord){
        return repository.findByPatient_IdAndClinicalRecordContainingIgnoreCase(patientId, clinicalRecord);
    }

    @Transactional
    @Override
    public ClinicalData save(ClinicalData clinicalData) {
        return repository.save(clinicalData);
    }

    @Override
    public ClinicalData getById(long id) {
        return repository.findById(id).get();
    }

    @Transactional
    @Override
    public ClinicalData update(long id, ClinicalData clinicalData) {
        ClinicalData current = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClinicalData not found with ID: " + id));

        current.setClinicalRecord(clinicalData.getClinicalRecord());

        return repository.save(current);
    }

    @Transactional

    @Override
    public void deleteById(long id){
        repository.deleteById(id);
    }

}
