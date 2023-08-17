package com.example.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    /*
     * deleteMessage
     * updateMessage
     * getMessageByAccount
     * getMessageByID
     * getAllMessages
     * newMessage
     */

    @Query("INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (:posted_by, :message_text, time_posted_epoch)")
    Message message();
    
}
