package com.example.ludikgames.aspects;

import com.example.ludikgames.model.User;
import com.example.ludikgames.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice("com.example.ludikgames.controller.authenticated_pages.with_chat")
@RequiredArgsConstructor
public class ChatAttributesAspect{

    private final ChatService chatService;

    @ModelAttribute
    public void addNicknameAndBalanceModels(Model model) {
        model.addAttribute("chatMessages", chatService.getMessages());
        model.addAttribute("isAuthenticated", true);
    }
}
