package com.example.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Modifying
    @Query(
        value = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (:postedBy, :messageText, :timePostedEpoch)",
        nativeQuery = true
    )
    void newMessage(
        @Param("postedBy") int posted_by, 
        @Param("messageText") String message_text, 
        @Param("timePostedEpoch") long time_posted_epoch
    );

    @Query(value = "SELECT * FROM message", nativeQuery = true)
    List<Message> getAllMessages();
    
    @Query(
        value = "SELECT * FROM message WHERE message_id = :messageId",
        nativeQuery = true
    )
    Optional<Message> getMessageByID(@Param("messageId")int message_id);

    @Query(
        value = "SELECT * FROM message WHERE posted_by = :postedBy",
        nativeQuery = true
    )
    List<Message> getMessageByAccount(@Param("postedBy")int posted_by);

    @Modifying
    @Query(
        value = "UPDATE message SET message_text = :messageText WHERE message_id = :messageId",
        nativeQuery = true
    )
    void updateMessage(
        @Param("messageText") String message_text, 
        @Param("messageId") int message_id
    );

    @Modifying
    @Query(
        value = "DELETE FROM message WHERE message_id = :messageId",
        nativeQuery = true
    )
    void deleteMessage(@Param("messageId") int message_id);
}
