package com.hackathon.hertrack.service;

import com.hackathon.hertrack.model.Cycle;
import com.hackathon.hertrack.model.Symptom;
import com.hackathon.hertrack.repository.CycleRepo;
import com.hackathon.hertrack.repository.SymptomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SymptomService {
    @Autowired
    private SymptomRepo symptomRepo;
    public void saveDetails(Symptom symptom){
        symptomRepo.save(symptom);
    }
    public void deleteDetails(Symptom symptom){
        symptomRepo.delete(symptom);
    }
    public Symptom getDetails(Long id){
        return symptomRepo.findById(id).get();
    }
    public Optional<Symptom> findById(Long id){
        return symptomRepo.findById(id);
    }
    public void checkSymptom(Symptom symptom){
        if (symptom.getCramps() == null) symptom.setCramps(false);
        if (symptom.getFatigue() == null) symptom.setFatigue(false);
        if (symptom.getHeadache() == null) symptom.setHeadache(false);
        if (symptom.getNausea() == null) symptom.setNausea(false);
        if (symptom.getMoodSwings() == null) symptom.setMoodSwings(false);
        if (symptom.getHunger() == null) symptom.setHunger(false);
        if (symptom.getHotFlashes() == null) symptom.setHotFlashes(false);
        if (symptom.getAcne() == null) symptom.setAcne(false);
    }
}

