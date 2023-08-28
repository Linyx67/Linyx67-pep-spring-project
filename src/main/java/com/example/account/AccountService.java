package com.example.account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    
    public Optional<Account> registerAccount(String username, String password){
        if(username==null || username.isBlank() || password.length()<4){
            return Optional.empty();
        } else {
            Optional<Account> optionalAccount = accountRepository.findByUsername(username);
            if(optionalAccount.isPresent()){
                return Optional.empty();
            } else {
                accountRepository.registerAccount(username, password);
                return accountRepository.findByUsername(username);
            }
        }
    } 

    public Optional<Account> loginAccount(String username, String password){
        return accountRepository.login(username, password);
    }

}
