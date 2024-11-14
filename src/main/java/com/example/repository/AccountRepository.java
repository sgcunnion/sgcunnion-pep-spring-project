package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;





@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


    //this is used by AccountService.registerUser() and MessageService.postMessage()
    public boolean existsByUsername(String username);

    //this is used by AccountService.login()
    public Optional<Account> findByUsernameAndPassword(String username, String password);

}
