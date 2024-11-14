package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import com.example.exception.InvalidRegistrationException;
import com.example.exception.AccountAlreadyFoundException;


@Service
public class AccountService {

    AccountRepository accRepo;

    public AccountService(AccountRepository accountRepository) { //Cynthia said I needed to have accountRepository as a param here for constructor injection
        this.accRepo = accountRepository;
    }


    //--------------------------------METHODS--------------------------------------------------


//REGISTER USER
    public Account registerUser(Account account) throws InvalidRegistrationException, AccountAlreadyFoundException {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4)
            throw new InvalidRegistrationException();

        else if (accRepo.existsByUsername(account.getUsername()))
            throw new AccountAlreadyFoundException();

        else {
            return accRepo.save(account);
        }
    }

//LOGIN
    public Optional<Account> login(String username, String password) {
        return accRepo.findByUsernameAndPassword(username, password);
    }



}
