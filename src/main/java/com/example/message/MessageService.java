package com.example.message;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.account.AccountRepository;




@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Message> getAllMessages(){
        return messageRepository.getAllMessages();
    }


    public Optional<Message> getMessageByID(int message_id){
        return messageRepository.getMessageByID(message_id);
    }

    public List<Message> getMessageByAccount(int posted_by){
        return messageRepository.getMessageByAccount(posted_by);
    }

    public Optional<Message> newMessage(int posted_by, String message_text, long time_posted_epoch){
        if(accountRepository.findById(posted_by).isPresent()){
            if(message_text==null || message_text.isBlank() || message_text.length()>254){
                return Optional.empty();
            }
            else{
                Message newMessage = new Message(posted_by, message_text, time_posted_epoch);
                return Optional.of(messageRepository.save(newMessage));
                /*
                 * The jpa repository .save() method is the preferred way of persisting information and
                 * obtaining the persisted record however in this case since the appliaction manually adds 
                 * records to the database as shown in the data.sql file on initialisaiton. The .save() method 
                 * causes a primary key violation since on the first call it attempts to add a message_id of 1 but this already exists in the 
                 * database. Removing these insertions from the data.sql file allows the application to work as expected.
                 */

                //messageRepository.newMessage(posted_by, message_text, time_posted_epoch);
                //return messageRepository.getMessageByTimeAndAccount(time_posted_epoch, posted_by);
            }
        } else{
            return Optional.empty();
        }
        
    }

    public void deleteMessage(int message_id){
        messageRepository.deleteMessage(message_id);
    }
    
    public Optional<Message> updateMessage (int message_id, String message_text){
        Optional<Message> updatedMessage = messageRepository.getMessageByID(message_id);
        if (updatedMessage.isPresent()){
            if(message_text==null || message_text.isBlank() || message_text.length()>254){
                return Optional.empty();
            }
            messageRepository.updateMessage(message_text, message_id);
            return messageRepository.getMessageByID(message_id);
        }
        else{
            return Optional.empty();
        }
    }
}
