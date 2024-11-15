package com.example.controller;

import org.springframework.stereotype.Controller;               //aren't I using this with @RestController?
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;                      //what is this? should I be using it?
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accServ;
    MessageService messServ;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accServ = accountService;
        this.messServ = messageService;
    }



    //------------------------------ACCOUNT METHODS-------------------------------------------------


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)                                  //sets default but OK is default anyway
    public ResponseEntity<Account> registerUser(@RequestBody Account account) throws 
        InvalidRegistrationException, AccountAlreadyFoundException {

        Account savedAccount = accServ.registerUser(account);
        return ResponseEntity.ok(savedAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login (@RequestBody Account account) {
        Optional<Account> loginResult = accServ.login(account.getUsername(), account.getPassword());
        boolean emptyLogin = loginResult.isEmpty();
        /*if (loginResult.isEmpty()) 
            return ResponseEntity.status(401).body(null);
        else
            return ResponseEntity.ok(loginResult.get());
        */
        return ResponseEntity.status(emptyLogin ? 401 : 200).body(loginResult.orElse(null));
    }



    //--------------------------------MESSAGE METHODS----------------------------------------


//POST MESSAGE
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage (@RequestBody Message message) {
        Message returnedMessage = messServ.postMessage(message);
        if (returnedMessage == null)
            return ResponseEntity.status(400).body(null);
        else
            return ResponseEntity.ok(returnedMessage);
    }


//GET MESSAGE BY ID
    @GetMapping("/messages/{messageID}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageID) {
        return ResponseEntity.ok(messServ.retrieveMessageByID(messageID));
    }


//GET ALL MESSAGES
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messServ.retrieveAllMessages());
    }


//GET ALL MESSAGES FROM USER
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromUser(@PathVariable int accountId) {
        return ResponseEntity.ok(messServ.retrieveAllMessagesFromUser(accountId));
    }


//UPDATE MESSAGE
    @PatchMapping("/messages/{messageID}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageID, @RequestBody Message message) 
        throws InvalidMessageException, MessageNotFoundException {
        return ResponseEntity.ok(messServ.updateMessageById(messageID, message.getMessageText()));
    }


//DELETE MESSAGE
    @DeleteMapping("/messages/{messageID}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageID) {
        Integer rowsDeleted = messServ.deleteMessageById(messageID);
        if (rowsDeleted == 1)
            return ResponseEntity.ok(rowsDeleted);
        else
            return ResponseEntity.ok(null);
    }



   //---------------------EXCEPTION HANDLING--------------------------------------------------------


    @ExceptionHandler(InvalidRegistrationException.class)
    public ResponseEntity<String> handleInvalidRegistration() {
        return ResponseEntity.status(400).body("Invalid registration credentials");
    }
    //may need to remove body
    //.build()

    @ExceptionHandler(AccountAlreadyFoundException.class)
    public ResponseEntity<String> handleAccountAlreadyFound() {
        return ResponseEntity.status(409).body("Credentials entered match existing account. Please sign in.");
    }

    @ExceptionHandler(InvalidMessageException.class)
    public ResponseEntity<String> handleInvalidMessage() {
        return ResponseEntity.status(400).body("The entered message is invalid.");
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> handleMessageNotFound() {
        return ResponseEntity.status(400).body("No message of the provided ID was found.");
    }

    
}
