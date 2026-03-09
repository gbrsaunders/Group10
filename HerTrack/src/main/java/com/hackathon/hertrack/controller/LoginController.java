package com.hackathon.hertrack.controller;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.service.AccountService;
import com.hackathon.hertrack.validator.AccountValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new AccountValidator(accountService.getAccountRepo()));
    }

    @RequestMapping("/")
    public String homePage() {
        return "redirect:/login";
    }
    // Register account
    @PostMapping("/register")
    public String createAccount(@Valid @ModelAttribute Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        account.setRole("USER");
        accountService.saveDetails(account);
        return "redirect:/login";
    }
    // Check logins and see if password and user is correct as well as assign USER role
    @RequestMapping("/checkLogin")
    public String checkLogin(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes, HttpSession session) {
        Account acc = accountService.findByUsername(username);
        if (acc != null && acc.getPassword().equals(password)) {
            session.setAttribute("accountID", acc.getId());
            return "redirect:/dashboard";
        }
        redirectAttributes.addFlashAttribute("error", "Account does not exist or password is incorrect");
        return "redirect:/login";
    }
    // Login
    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("account", new Account());
        return "login";
    }
    // Signup
    @RequestMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("account", new Account());
        return "signup";
    }
    // Logout
    @RequestMapping("/logout")
    public String signOut(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}