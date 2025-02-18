package com.polis.polishospital.controller;

import com.polis.polishospital.entity.AdmissionState;
import com.polis.polishospital.entity.AdmissionState;
import com.polis.polishospital.entity.ClinicalData;
import com.polis.polishospital.service.AdmissionStateService;
import com.polis.polishospital.service.AdmissionStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admission-state")
public class AdmissionStateRESTController {

    @Autowired
    private AdmissionStateService service;


    @GetMapping
    public ResponseEntity<List<AdmissionState>> getAll() {
        List<AdmissionState> admissionStates = service.getAll();
        return new ResponseEntity<>(admissionStates, HttpStatus.OK); // HTTP 200 OK
    }

    @GetMapping("/patient/{patient_id}")
    public ResponseEntity<List<AdmissionState>> getByPatientId(@PathVariable long patient_id) {
        List<AdmissionState> admissionStates = service.getByPatientId(patient_id);
        return new ResponseEntity<>(admissionStates, HttpStatus.OK); // HTTP 200 OK
    }

    @GetMapping("/is-discharged/{patient_id}")
    public ResponseEntity<Boolean> isPatientDischarged(@PathVariable long patient_id) {
        boolean hasActive = service.hasActiveAdmissionState(patient_id);
        return new ResponseEntity<>(hasActive, HttpStatus.OK); // HTTP 200 OK
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AdmissionState> getById(@PathVariable long id) {
        AdmissionState admissionState = service.getById(id);
        if (admissionState == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admissionState, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdmissionState> create(@RequestBody AdmissionState admissionState) {
        AdmissionState createdAdmissionState = service.save(admissionState);
        return new ResponseEntity<>(createdAdmissionState, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdmissionState> update(@PathVariable long id, @RequestBody AdmissionState admissionState) {
        AdmissionState updatedAdmissionState = service.update(id, admissionState);
        return ResponseEntity.ok(updatedAdmissionState);
    }

    @PutMapping("/discharge/{id}")
    public ResponseEntity<AdmissionState> dischargePatient(@PathVariable long id, @RequestBody AdmissionState admissionState) {
        AdmissionState updatedAdmissionState = service.dischargePatient(id, admissionState);
        return ResponseEntity.ok(updatedAdmissionState);
    }

    @PutMapping("/updateDepartment/{id}/{departmentId}")
    public ResponseEntity<AdmissionState> updateDepartment(@PathVariable long id, @PathVariable long departmentId) {
        AdmissionState updatedAdmissionState = service.updateDepartment(id, departmentId);
        return ResponseEntity.ok(updatedAdmissionState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
