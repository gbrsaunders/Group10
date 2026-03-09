package com.hackathon.hertrack.validator;


import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.repository.AccountRepo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {
    // Validates before making an Account
    private AccountRepo accountRepo;
    public AccountValidator(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }
    // To check the inputs with standards which can be added
    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        if(accountRepo.findByEmail(account.getEmail()) != null){
            errors.rejectValue("email", "", "Email already exists");
        }
        if (account.getPassword().length() < 8) {
            errors.rejectValue("password", "", "Password must be at least 8 characters");
        }
        if (account.getPassword().length() > 16) {
            errors.rejectValue("password", "", "Password must be less than 16 characters");
        }
    }

}
