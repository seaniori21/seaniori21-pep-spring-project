package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.IllegalArgumentException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository MessageRepository, AccountRepository AccountRepository){
        this.messageRepository = MessageRepository;
        this.accountRepository = AccountRepository;
    }

    /**
     * Create a new Message.
     * @param Message a transient Message entity.
     * @return a registered Message entity.
     */
    public Message createMessage(Message newMessage) throws IllegalArgumentException{
        if (newMessage.getMessageText() == null || newMessage.getMessageText().isBlank()) {
            throw new IllegalArgumentException("message text cannot be blank");
        }
        if(newMessage.getMessageText().length() > 255){
            throw new IllegalArgumentException("message text cannot > 255 characters");
        }
        Optional<Account> account = accountRepository.findById(newMessage.getPostedBy());
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("posted By UserID cannot be found");
        }
        return messageRepository.save(newMessage); 
    }

    // /**
    //  * Login a new Message.
    //  * @param String username
    //  * @param String password
    //  * @return a registered Message entity.
    //  */
    // public Optional<Message> loginMessage(String username, String password) {
    //     Optional<Message> MessageOptional = MessageRepository.findByUsername(username);
    //     if (MessageOptional.isPresent()) {
    //         Message Message = MessageOptional.get();
    //         if (Message.getPassword().equals(password)) {
    //             return Optional.of(Message);  // Successful login
    //         }
    //     }
    //     return Optional.empty();  // Login failed
    // }

    /**
     * Get a message by its ID
     * @param messageId id of message
     * @return the message
     */
    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId); 
    }

    /**
     * Delete a message by its ID
     * @param messageId id of message
     * @return Integer number of rows deleted
     */
    public Integer deleteMessageById(Integer messageId) {
        Optional<Message> msg = messageRepository.findById(messageId);
        if(msg.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }else{
            return 0; 
        } 
    }

    /**
     * Update a message by its ID
     * @param messageId id of message
     * @return Integer number of rows updated
     */
    public Integer updateMessageById(Integer messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.isBlank()) {
            throw new IllegalArgumentException("message text cannot be blank");
        }
        if(newMessageText.length() > 255){
            throw new IllegalArgumentException("message text cannot > 255 characters");
        }


        Optional<Message> msg = messageRepository.findById(messageId);
        if(msg.isPresent()){
            Message newMsg = msg.get();
            newMsg.setMessageText(newMessageText);
            messageRepository.save(newMsg);
            return 1;
        }else{
            throw new ResourceNotFoundException("message not found");
        } 
    }

    /**
     * @return all persisted Message entities.
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * @return all Message entities by given userId.
     */
    public List<Message> getAllMessagesByUserId(Integer postedBy){
        return messageRepository.findByPostedBy(postedBy);
    }
}
