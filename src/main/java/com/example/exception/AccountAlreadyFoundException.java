package com.example.exception;

public class AccountAlreadyFoundException extends Throwable {
    
    public AccountAlreadyFoundException() {
        System.out.println("There was an attempt to create an account with a username that already exists.");
    }
}
