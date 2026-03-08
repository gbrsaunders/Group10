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
    private Boolean cramps;
    private Boolean fatigue;
    private Boolean headache;
    private Boolean nausea;
    private Boolean moodSwings;
    private Boolean hunger;
    private Boolean hotFlashes;
    private Boolean acne;
}
