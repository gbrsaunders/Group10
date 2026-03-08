package com.hackathon.hertrack.service;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Cycle;
import com.hackathon.hertrack.repository.AccountRepo;
import com.hackathon.hertrack.repository.CycleRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    @Getter
    private AccountRepo accountRepo;

    @Autowired
    private CycleRepo cycleRepo;

    public void saveDetails(Account account){
        accountRepo.save(account);
    }
    public void deleteDetails(Account account){
        accountRepo.delete(account);
    }
    public Account getDetails(Long id){
        return accountRepo.findById(id).get();
    }
    public Account findById(Long id){
        return accountRepo.findById(id).get();
    }
    public Account findByUsername(String username){
        return accountRepo.findByUsername(username);
    }
    public Cycle calculatePeriod(Account account){
        Cycle predicted = null;
        List<Cycle> cycles = account.getCycles();
        System.out.println(cycles.size());
        if(!cycles.isEmpty()){
            predicted = new Cycle();
            Long totalLength = 0L;
            for(int i = 0; i < account.getCycles().size(); i++){
                if(cycles.get(i) == null && cycles.get(i+1) == null){
                    break;
                }
                totalLength = totalLength + ChronoUnit.DAYS.between(cycles.get(i).getStartDate(), cycles.get(i+1).getStartDate());
            }
            long averageCycleLength = totalLength / account.getCycles().size();
            LocalDate nextCycleStartDate = cycles.getLast().getStartDate().plusDays(averageCycleLength);
            if (ChronoUnit.DAYS.between(cycles.getLast().getStartDate(), LocalDate.now()) < 6){
                account.setCurrentPhase("Menstrual");
            }
            else if (ChronoUnit.DAYS.between(cycles.getLast().getStartDate(), LocalDate.now()) < 14){
                account.setCurrentPhase("Follicular");
            }
            else if (ChronoUnit.DAYS.between(cycles.getLast().getStartDate(), LocalDate.now()) < 17){
                account.setCurrentPhase("Ovulation");
            }
            else{
                account.setCurrentPhase("Luteal");
            }
            predicted.setLength((int) averageCycleLength);
            predicted.setStartDate(nextCycleStartDate);
            predicted.setAccount(account);
            predicted.setSymptom(cycles.getLast().getSymptom());
            cycleRepo.save(predicted);
            accountRepo.save(account);
        }
        return predicted;
    }
}
