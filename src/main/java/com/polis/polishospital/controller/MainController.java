package com.polis.polishospital.controller;

import com.polis.polishospital.entity.Patient;
import com.polis.polishospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class MainController {

    @GetMapping({"/", "/home"})
    public String home(){
        return "home/home";
    }

    @GetMapping("/patients")
    public String patients(){
        return "patients/patients";
    }

    @GetMapping("/patients/create-patient")
    public String createPatients(){
        return "patients/create-patient";
    }

    @GetMapping("/patients/edit-patient")
    public String editPatients(){
        return "patients/edit-patient";
    }

    @GetMapping("/departments")
    public String departments(){
        return "departments/department";
    }

    @GetMapping("/departments/create-department")
    public String createDepartments(){
        return "departments/create-department";
    }

    @GetMapping("/departments/edit-department")
    public String editDepartments() {
        return "departments/edit-department";
    }

    @GetMapping("/patients/clinical-data")
    public String clinicalData(){
        return "clinical-data/clinical-data";
    }

    @GetMapping("/patients/clinical-data/edit")
    public String editClinicalData(){
        return "clinical-data/edit-clinical-data";
    }

    @GetMapping("/admission-state/create-admission-state")
    public String createAdmissionState(){
        return "admission-state/create-admission-state";
    }

    @GetMapping("/admission-state/edit-department")
    public String admissionStateEditDepartment(){
        return "admission-state/edit-department";
    }

    @GetMapping("/admission-state")
    public String admissionState(){
        return "admission-state/admission-state";
    }

    @GetMapping("/admission-state/discharge")
    public String admissionStateDischarge(){
        return "admission-state/discharge";
    }
}
