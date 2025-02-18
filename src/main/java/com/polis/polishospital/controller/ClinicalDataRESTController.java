package com.polis.polishospital.controller;

import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.entity.Patient;
import com.polis.polishospital.service.ClinicalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clinical-data")
public class ClinicalDataRESTController {

    @Autowired
    private ClinicalDataService service;


    @GetMapping
    public ResponseEntity<List<ClinicalData>> getAll() {
        List<ClinicalData> clinicalDatas = service.getAll();
        return new ResponseEntity<>(clinicalDatas, HttpStatus.OK); // HTTP 200 OK
    }

    @GetMapping("/patient/{patient_id}")
    public ResponseEntity<List<ClinicalData>> getClinicalDataByPatientId(@PathVariable long patient_id) {
        List<ClinicalData> clinicalDatas = service.getByPatientId(patient_id);
        return new ResponseEntity<>(clinicalDatas, HttpStatus.OK); // HTTP 200 OK
    }

    @GetMapping("/patient/record/{patient_id}")
    public List<ClinicalData> getClinicalDataByPatientIdAndRecord(
            @PathVariable long patient_id,
            @RequestParam(required = false, defaultValue = "") String clinicalRecord) {
        return service.getByPatientIdAndRecord(patient_id, clinicalRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicalData> getById(@PathVariable long id) {
        ClinicalData clinicalData = service.getById(id);
        if (clinicalData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clinicalData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClinicalData> create(@RequestBody ClinicalData clinicalData) {
        ClinicalData createdClinicalData = service.save(clinicalData);
        return new ResponseEntity<>(createdClinicalData, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicalData> update(@PathVariable long id, @RequestBody ClinicalData clinicalData) {
        ClinicalData updatedClinicalData = service.update(id, clinicalData);
        return ResponseEntity.ok(updatedClinicalData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
