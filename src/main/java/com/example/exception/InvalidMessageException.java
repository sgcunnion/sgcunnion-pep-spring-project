package com.example.exception;

public class InvalidMessageException extends Throwable {
    
    public InvalidMessageException() {
        System.out.println("Attempt to post or update an invalid message.");
    }
}
