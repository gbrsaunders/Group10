package com.hackathon.hertrack.repository;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleRepo extends JpaRepository<Cycle, Long> {
}
