package com.hackathon.hertrack.service;

import com.hackathon.hertrack.model.Bathroom;
import com.hackathon.hertrack.repository.BathroomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BathroomService {
    @Autowired
    private BathroomRepo bathroomRepo;

    public List<Bathroom> findAll(){
        return bathroomRepo.findAll();
    }
}
