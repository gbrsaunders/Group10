package com.hackathon.hertrack.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private Integer length;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Symptom symptom;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}