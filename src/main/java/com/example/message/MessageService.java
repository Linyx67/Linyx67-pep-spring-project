package com.example.message;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.account.AccountRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;

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
                return null;
            }
            else{
                messageRepository.newMessage(posted_by, message_text, time_posted_epoch);
                return messageRepository.getMessageByTimeAndAccount(time_posted_epoch, posted_by);
            }
        } else{
            return null;
        }
        
    }

    public void deleteMessage(int message_id){
        messageRepository.deleteMessage(message_id);
    }

    public Optional<Message> updateMessage (int message_id, String message_text){
        Optional<Message> updatedMessage = messageRepository.getMessageByID(message_id);
        if (updatedMessage.isPresent()){
            messageRepository.updateMessage(message_text, message_id);
            return messageRepository.getMessageByID(message_id);
        }
        else{
            return null;
        }
    }
}
