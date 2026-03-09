package com.hackathon.hertrack;

import com.hackathon.hertrack.model.Bathroom;
import com.hackathon.hertrack.repository.BathroomRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HerTrackApplication {

    @Autowired
    BathroomRepo bathroomRepo;

    public static void main(String[] args) {
        SpringApplication.run(HerTrackApplication.class, args);
    }
    @PostConstruct
    public void init(){{
        Bathroom b = new Bathroom("b", "b", "Stocked");
        bathroomRepo.save(b);
    }}

}
