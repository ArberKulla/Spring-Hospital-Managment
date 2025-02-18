package com.polis.polishospital.controller;

import com.polis.polishospital.dto.LongDto;
import com.polis.polishospital.dto.PatientCreateDto;
import com.polis.polishospital.dto.PatientDto;
import com.polis.polishospital.dto.StringDto;
import com.polis.polishospital.entity.Department;
import com.polis.polishospital.entity.Patient;
import com.polis.polishospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patients")
public class PatientRESTController {

    @Autowired
    private PatientService service;


    @GetMapping
    public ResponseEntity<List<PatientDto>> getAll() {
        List<PatientDto> patients = service.getAll();
        return new ResponseEntity<>(patients, HttpStatus.OK); // HTTP 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable Long id) {
        PatientDto patient = service.getById(id);
        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientDto> create(@RequestBody PatientCreateDto patient) {
        PatientDto createdPatient = service.save(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PatientDto> update(@RequestBody PatientDto patient) {
        PatientDto updatedPatient = service.update(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody LongDto id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchByNameAndLastName")
    public ResponseEntity<List<PatientDto>> searchPatientsByNameAndLastName(
            @RequestParam(required = false, defaultValue = "") StringDto name,
            @RequestParam(required = false, defaultValue = "") StringDto lastName) {
        List<PatientDto> patients = service.findByNameAndLastname(name, lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);}

}
