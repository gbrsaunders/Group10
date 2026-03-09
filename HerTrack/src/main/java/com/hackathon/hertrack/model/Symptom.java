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

    public String toString() {
        String symptoms =  "";
        if(cramps == true){
            symptoms = symptoms + "cramps, ";
        }
        if(fatigue == true){
            symptoms = symptoms + "fatigue, ";
        }
        if(headache == true){
            symptoms = symptoms + "headache, ";
        }
        if(nausea == true){
            symptoms = symptoms + "nausea, ";
        }
        if(moodSwings == true){
            symptoms = symptoms + "mood swings, ";
        }
        if(hunger == true){
            symptoms = symptoms + "hunger, ";
        }
        if(hotFlashes == true){
            symptoms = symptoms + "hotFlashes, ";
        }
        if(acne == true){
            symptoms = symptoms + "acne, ";
        }
        return symptoms;
    }

    public void CopySymptoms(Symptom other) {
        this.cramps = other.getCramps();
        this.fatigue = other.getFatigue();
        this.headache = other.getHeadache();
        this.nausea = other.getNausea();
        this.moodSwings = other.getMoodSwings();
        this.hunger = other.getHunger();
        this.hotFlashes = other.getHotFlashes();
        this.acne = other.getAcne();
    }
}

