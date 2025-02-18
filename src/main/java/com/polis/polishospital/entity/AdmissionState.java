package com.polis.polishospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "AdmissionStates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "enteringDate", nullable = false)
    private LocalDateTime enteringDate;

    @Column(name = "exitingDate")
    private LocalDateTime exitingDate;

    @Column(name = "cause")
    private String cause;

    @Column(name = "reason")
    private String reason;

    @Column(name = "discharge", nullable = false)
    private Boolean discharge;

    @Enumerated(EnumType.STRING)
    @Column(name = "dischargeReason")
    private DischargeReason dischargeReason;

    @PrePersist
    protected void onCreate() {
        this.enteringDate = LocalDateTime.now(); // Default to current timestamp
        this.discharge = false; // Default to false
    }

}
