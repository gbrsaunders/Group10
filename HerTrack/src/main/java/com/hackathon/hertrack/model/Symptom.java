package com.hackathon.hertrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean cramps;
    private boolean fatigue;
    private boolean headache;
    private boolean nausea;
    private boolean moodSwings;
    private int hunger;
    private boolean hotFlashes;
    private boolean acne;
    private String other;
}
