package com.hackathon.hertrack.service;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Cycle;
import com.hackathon.hertrack.model.Symptom;
import com.hackathon.hertrack.repository.AccountRepo;
import com.hackathon.hertrack.repository.CycleRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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
    public List<Account> findAll(){
        return accountRepo.findAll();
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
        List<Cycle> allCycles = account.getCycles();
        allCycles.sort(Comparator.comparing(Cycle::getStartDate));
        List<Cycle> cycles = allCycles.stream().skip(Math.max(0, allCycles.size() - 6)).toList();
        if (cycles.size() >= 2) {
            predicted = new Cycle();
            long totalLength = 0;
            for (int i = 0; i < cycles.size() - 1; i++) {
                LocalDate start1 = cycles.get(i).getStartDate();
                LocalDate start2 = cycles.get(i + 1).getStartDate();
                totalLength += ChronoUnit.DAYS.between(start1, start2);
            }
            long averageCycleLength = totalLength / (cycles.size() - 1);
            LocalDate lastStart = cycles.getLast().getStartDate();
            LocalDate nextCycleStartDate = lastStart.plusDays(averageCycleLength);
            while(nextCycleStartDate.isBefore(LocalDate.now())){
                nextCycleStartDate = nextCycleStartDate.plusDays(averageCycleLength);
            }
            predicted.setLength((int) ChronoUnit.DAYS.between(lastStart, nextCycleStartDate));
            long daysSinceLast = ChronoUnit.DAYS.between(lastStart, LocalDate.now()) % predicted.getLength();
            if (daysSinceLast < 6) {
                account.setCurrentPhase("Menstrual");
            } else if (daysSinceLast < 14) {
                account.setCurrentPhase("Follicular");
            } else if (daysSinceLast < 17) {
                account.setCurrentPhase("Ovulation");
            } else {
                account.setCurrentPhase("Luteal");
            }
            predicted.setStartDate(nextCycleStartDate);
            Symptom predictedSymptom = new Symptom();
            predictedSymptom.CopySymptoms(cycles.getLast().getSymptom());
            predicted.setSymptom(predictedSymptom);
        }
        return predicted;
    }
}
