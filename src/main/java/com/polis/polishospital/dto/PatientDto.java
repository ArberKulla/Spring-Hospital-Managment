package com.polis.polishospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientDto {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
}
