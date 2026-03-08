package com.hackathon.hertrack.repository;

import com.hackathon.hertrack.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByEmail(String email);
    Account findById(long ID);
}
