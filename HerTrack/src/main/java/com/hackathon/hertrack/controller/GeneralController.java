package com.hackathon.hertrack.controller;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.Cycle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class GeneralController {
    @RequestMapping("/resources")
    private String resource(){
        return "app/extra-resources";
    }
    @RequestMapping("/logPeriod")
    private String periodTracker(Model model){
        model.addAttribute("cycle", new Cycle());
        return "app/log-period";
    }
}
