package com.example.exception;

public class InvalidRegistrationException extends Throwable  {

    public InvalidRegistrationException() {
        System.out.println("There was an attempt to register an account with invalid credentials.");
    }
}