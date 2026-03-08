package com.hackathon.hertrack.service;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Cycle;
import com.hackathon.hertrack.repository.AccountRepo;
import com.hackathon.hertrack.repository.CycleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CycleService {
    @Autowired
    private CycleRepo cycleRepo;

    public void saveDetails(Cycle cycle){
        cycleRepo.save(cycle);
    }
    public void deleteDetails(Cycle cycle){
        cycleRepo.delete(cycle);
    }
    public Cycle getDetails(Long id){
        return cycleRepo.findById(id).get();
    }
    public Optional<Cycle> findById(Long id){
        return cycleRepo.findById(id);
    }
    public List<Cycle> findAll(){
        return cycleRepo.findAll();
    }
}
