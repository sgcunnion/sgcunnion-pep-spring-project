package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import com.example.exception.*;

//edit for testing commit

@Service
public class MessageService {

    MessageRepository messRepo;
    AccountRepository accRepo;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messRepo = messageRepository;
        this.accRepo = accountRepository;   //should be the same instance as messServ's accRepo due to default Singleton scope of beans
    }


    //------------------------METHODS--------------------------------------------------------------

//POST MESSAGE
    public Message postMessage(Message message) {
        if (message.getMessageText().isEmpty() == false && message.getMessageText().length() < 255) {
            if (accRepo.existsById(message.getPostedBy()))
                return messRepo.save(message);
        }
        return null;
    }


//RETRIEVE MESSAGE BY ID
    public Message retrieveMessageByID(int messageID) {
        return messRepo.findById(messageID).orElse(null);
    }


//RETRIEVE ALL MESSAGES
    public List<Message> retrieveAllMessages() {
        return messRepo.findAll();
    }


//RETRIEVE ALL MESSAGES FROM USER
    public List<Message> retrieveAllMessagesFromUser(int accountID) {                                           
        return messRepo.findAllByPostedBy(accountID);
    }


//UPDATE MESSAGE
    public Integer updateMessageById(int messageID, String messageText) 
        throws InvalidMessageException, MessageNotFoundException {
        if (messageText.isBlank() || messageText.length() > 255)   
            throw new InvalidMessageException();                           
        else if (messRepo.existsById(messageID) == false)
            throw new MessageNotFoundException();
        
        return messRepo.updateMessage(messageID, messageText);      //returns number of rows updated
    }


//DELETE MESSAGE
    public Integer deleteMessageById(int messageID) {
        return messRepo.customDeleteById(messageID);                //returns number of rows updated
    }
    
}
