package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.account.Account;
import com.example.account.AccountService;
import com.example.message.Message;
import com.example.message.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 *
 * You are expected to have at least the following methods: getAllMessages, getMessageByID, getMessageByAccount, registerAccount, newMessage, login, deleteMessage.
 * 
 * These methods should follow proper naming conventions, and include appropriate arguments. Refer to the tests provided for more details.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageByID(@PathVariable int message_id){
        Optional<Message> message = messageService.getMessageByID(message_id);
        if(message.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(message.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessageByAccount(@PathVariable int account_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageByAccount(account_id));
    }
    /*
     * The following methods registerAccount, login and newMessage use the @RequestBody annotation to deserialize 
     * the information in json format to an object. This however causes the annotation test to fail since the test
     * code incorrectly expects these methods to accept strings as the input parameters.
     */
    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Optional<Account> addedAccount = accountService.registerAccount(account.getUsername(), account.getPassword());
        if (addedAccount.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(addedAccount.get());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    } 
   
    @PostMapping("messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message){
        Optional<Message> optionalMessage = messageService.newMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        if(optionalMessage.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalMessage.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Optional<Account> loggedAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
        if(loggedAccount.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(loggedAccount.get());
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable int message_id){
        Optional<Message> deletedMessage = messageService.getMessageByID(message_id);
        if (deletedMessage.isPresent()){
            messageService.deleteMessage(message_id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedMessage.get());
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    } 

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Message> updateMessage(@RequestBody Message message, @PathVariable int message_id){
       Optional<Message> updatedMessage = messageService.updateMessage(message_id, message.getMessage_text());
        if(updatedMessage.isPresent()){
            updatedMessage.get().setMessage_text(message.getMessage_text());
            return ResponseEntity.status(HttpStatus.OK).body(updatedMessage.get());
       }
       else{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
       
    }

}
