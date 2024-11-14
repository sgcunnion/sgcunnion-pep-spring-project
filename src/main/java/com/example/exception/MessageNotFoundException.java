package com.example.exception;

public class MessageNotFoundException extends Throwable {
    
    public MessageNotFoundException() {
        System.out.println("There was an attempt to update a message that doesn't exist.");
    }
}
