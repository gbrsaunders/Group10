package com.hackathon.hertrack.controller;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Cycle;
import com.hackathon.hertrack.model.Symptom;
import com.hackathon.hertrack.repository.CycleRepo;
import com.hackathon.hertrack.service.AccountService;
import com.hackathon.hertrack.service.BathroomService;
import com.hackathon.hertrack.service.CycleService;
import com.hackathon.hertrack.service.SymptomService;
import jakarta.servlet.http.HttpSession;
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
    BathroomService bathroomService;
    @Autowired
    CycleService cycleService;
    @Autowired
    AccountService accountService;
    @Autowired
    SymptomService symptomService;

    private long getAccountId(HttpSession session) {
        return (long) session.getAttribute("accountID");
    }

    @RequestMapping("/admin")
    private String admin(HttpSession session, Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "app/admin";
    }

    @RequestMapping("/resources")
    private String resource(HttpSession session, Model model) {
        return "app/extra-resources";
    }

    @RequestMapping("/dashboard")
    private String dashboard(HttpSession session, Model model) {
        return "app/dashboard";
    }

    @RequestMapping("/logPeriod")
    private String periodTracker(HttpSession session, Model model) {
        Cycle cycle = new Cycle();
        cycle.setSymptom(new Symptom());
        model.addAttribute("cycle", cycle);
        return "app/log-period";
    }

    @RequestMapping("/uniResources")
    private String uniResources(HttpSession session, Model model) {
        model.addAttribute("bathrooms", bathroomService.findAll());
        return "app/university-resources";
    }

    @PostMapping("/addCycle")
    private String addCycle(HttpSession session, @ModelAttribute Cycle cycle, Model model) {
        if (cycle.getLength() == null) {
            return "app/login";
        }
        if (cycle.getSymptom() == null) {
            cycle.setSymptom(new Symptom());
        }
        long accountID = getAccountId(session);
        Account acc = accountService.findById(accountID);
        if (cycle.getStartDate().isBefore(LocalDate.now())) {
            symptomService.checkSymptom(cycle.getSymptom());
            List<Cycle> cycles = acc.getCycles();
            cycle.setAccount(acc);
            cycles.add(cycle);
            acc.setCycles(cycles);
            cycleService.saveDetails(cycle);
            accountService.saveDetails(acc);
        }
        return "redirect:/tracker";
    }

    @RequestMapping("/tracker")
    private String tracker(HttpSession session, Model model) {
        long accountID = getAccountId(session);
        Cycle prediction = accountService.calculatePeriod(accountService.findById(accountID));
        List<Cycle> cycles = accountService.findById(accountID).getCycles();
        model.addAttribute("prediction", accountService.calculatePeriod(accountService.getAccountRepo().findById(accountID)));
        if (prediction != null) {
            model.addAttribute("predictionDays", ChronoUnit.DAYS.between(LocalDate.now(), prediction.getStartDate()));
        }
        model.addAttribute("cycles", cycles);
        return "app/tracker";
    }
}