package com.example.controllers;

import com.example.model.Message;
import com.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
        try {
            return chatService.saveMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        try {
            return chatService.getAllMessages();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

