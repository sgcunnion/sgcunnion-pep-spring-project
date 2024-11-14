package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;



@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    //a custom delete method is necessary because default deleteById returns void and not the number of rows updated
    @Transactional
    @Modifying
    @Query("DELETE Message m WHERE m.messageId = :messageID")
    public int customDeleteById (@Param("messageID") int messageID); 
    

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageID")
    public int updateMessage(@Param("messageID") int messageID, @Param("messageText") String messageText);
    //may need to handle this in service class

    
    //for use by retrieveAllMessagesFromUser
    public List<Message> findAllByPostedBy(int accountID);
}
