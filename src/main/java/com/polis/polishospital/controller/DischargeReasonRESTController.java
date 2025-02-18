package com.polis.polishospital.controller;

import com.polis.polishospital.entity.AdmissionState;
import com.polis.polishospital.entity.DischargeReason;
import com.polis.polishospital.service.AdmissionStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/discharge-reasons")
public class DischargeReasonRESTController {

    @GetMapping
    public ResponseEntity<List<String>> getDischargeReasons() {
        List<String> dischargeReasons = Arrays.stream(DischargeReason.values())
                .map(Enum::name) // Converts enum values to their names
                .collect(Collectors.toList());

        return ResponseEntity.ok(dischargeReasons);
    }

}
