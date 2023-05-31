package com.example.ludikgames.controller;

import com.example.ludikgames.dto.MessageDto;
import com.example.ludikgames.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;

    @GetMapping("/messages")
    public String getMessagesPage(Model model) {
        model.addAttribute("chatMessages", chatService.getMessages());
        return "messages";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDto sendMessages(MessageDto messageData, Principal principal) {
        messageData.setFrom(principal.getName());

        return chatService.saveAndReturnMessageForChat(messageData);

    }
}
