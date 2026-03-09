package com.hackathon.hertrack.controller;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Cycle;
import com.hackathon.hertrack.model.Symptom;
import com.hackathon.hertrack.repository.CycleRepo;
import com.hackathon.hertrack.service.AccountService;
import com.hackathon.hertrack.service.CycleService;
import com.hackathon.hertrack.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class GeneralController {
    @Autowired
    CycleService cycleService;

    @Autowired
    AccountService accountService;

    @Autowired
    SymptomService symptomService;

    @RequestMapping("/resources")
    private String resource() {
        return "app/extra-resources";
    }
    @RequestMapping("/dashboard")
    private String dashboard(@RequestParam long accountID, Model model) {
        model.addAttribute("accountID", accountID);
        return "app/dashboard";
    }

    @RequestMapping("/logPeriod")
    private String periodTracker(@RequestParam long accountID, Model model) {
        Cycle cycle = new Cycle();
        cycle.setSymptom(new Symptom());
        model.addAttribute("accountID", accountID);
        model.addAttribute("cycle", cycle);
        return "app/log-period";
    }

    @PostMapping("/addCycle")
    private String addCycle(@RequestParam long accountID, @ModelAttribute Cycle cycle, Model model) {
        if (cycle.getLength() == null) {
            System.out.println("No");
            return "app/login";
        }
        if (cycle.getSymptom() == null){
            cycle.setSymptom(new Symptom());
        }
        Account acc = accountService.findById(accountID);
        if(cycle.getStartDate().isBefore(LocalDate.now())){
            symptomService.checkSymptom(cycle.getSymptom());
            List<Cycle> cycles = acc.getCycles();
            cycle.setAccount(acc);
            cycles.add(cycle);
            acc.setCycles(cycles);
            cycleService.saveDetails(cycle);
            accountService.saveDetails(acc);
            System.out.println("Saved at Account ID: " + acc.getId() + " and Cycle " + cycle.getId());
        }
        return "redirect:/tracker?accountID=" + acc.getId();

    }

    @RequestMapping("/tracker")
    private String tracker(@RequestParam long accountID,  Model model) {
        Cycle prediction = accountService.calculatePeriod(accountService.findById(accountID));
        List<Cycle> cycles = accountService.findById(accountID).getCycles();
        model.addAttribute("prediction", accountService.calculatePeriod(accountService.getAccountRepo().findById(accountID)));
        if (prediction != null){
            model.addAttribute("predictionDays", ChronoUnit.DAYS.between(prediction.getStartDate(), LocalDate.now()));
        }
        model.addAttribute("cycles", cycles );
        model.addAttribute("accountID", accountID);
        System.out.println("AccountID At Tracker " + accountID);
        return "app/tracker";
    }
    @RequestMapping("/chat")
    private String chat(){
        return "chat";
    }
}