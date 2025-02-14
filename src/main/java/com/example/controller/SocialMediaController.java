package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.MessageRepository;
import com.example.exception.IllegalArgumentException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController; 

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Here's an example of how a response can be sent with a custom status code, as well as an informational message
     * in the response body as a String.
     */
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(200).body("Hello World!");
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message msg) {
        try {
            Message createdMessage = messageService.createMessage(msg);
            return ResponseEntity.ok(createdMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }




    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.status(200).body(accounts);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        try {
            Account savedAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(savedAccount);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Username already exists.")) {
                return ResponseEntity.status(409).body(e.getMessage());
            }
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Account loginRequest) {
        Optional<Account> accountOptional = accountService.loginAccount(loginRequest.getUsername(), loginRequest.getPassword());

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return ResponseEntity.ok(account);  // 200 OK
        } else {
            return ResponseEntity.status(401).body("Unauthorized: Invalid username or password");
        }
    }

}
