package com.hackathon.hertrack.repository;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepo extends JpaRepository<Symptom, Long> {
    Symptom findById(long id);
}
