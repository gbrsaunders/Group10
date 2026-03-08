package com.hackathon.hertrack.controller;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.service.AccountService;
import com.hackathon.hertrack.validator.AccountValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoginController {
    // Required services for the controller
    @Autowired
    private AccountService accountService;


    // For validation and error messages
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new AccountValidator(accountService.getAccountRepo()));
    }
    @RequestMapping("/")
    public String homePage(Model model) {
        // As there is no home page yet
        return "redirect:/login";
    }
    // To display error messages and if successful redirect to login page.
    @PostMapping("/register")
    public String createAccount(@Valid @ModelAttribute Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        accountService.saveDetails(account);
        return "redirect:/login";
    }
    @RequestMapping("/showAccount")
    public String showAccount(long ID, Model model) {
        Account acc = accountService.getDetails(ID);
        model.addAttribute("account", acc);
        return "account";
    }
    @RequestMapping("/returnToMain")
    public String returnToProgress(@RequestParam Long accountID, Model model){
        // Used for the navigation bar to return to the progress page
        Account acc = accountService.getDetails(accountID);
        accountService.saveDetails(acc);
        return "mainpage";
    }
    @RequestMapping("/checkLogin")
    // If there is a successful login then redirect to the progress page with account details
    public String checkLogin(@RequestParam String email, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
        Account acc = accountService.findByEmail(email);
        // Checks if the password equals to the account which they the email is assigned to
        if(acc != null && acc.getPassword().equals(password)){
            model.addAttribute("accountID", acc.getId());
            return "app/tracker";
        }
        redirectAttributes.addFlashAttribute("error", "Account does not exist or password is incorrect");
        return "redirect:/login";
    }
    // Shows the login page
    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("account", new Account());
        return "login";
    }
    // Shows the signup page
    @RequestMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("account", new Account());
        return "signup";
    }
    // Signing out will lead you to to the login page
    @RequestMapping("/signout")
    public String signOut(Model model) {
        return "redirect:/login";
    }
    // Resets the passwords ( without authentication for now ) and save details.
}
