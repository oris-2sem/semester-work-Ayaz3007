package com.example.ludikgames.controller.authenticated_pages;

import com.example.ludikgames.dto.SupportChatIdDto;
import com.example.ludikgames.dto.SupportMessageDto;
import com.example.ludikgames.model.SupportChat;
import com.example.ludikgames.service.SupportChatService;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SupportController {
    private final UsersService usersService;
    private final SupportChatService supportChatService;

    @MessageMapping("/support-chat")
    @SendTo("/topic/support-messages")
    public SupportMessageDto sendMessage(SupportMessageDto message, Principal principal) {
        message.setSender(principal.getName());
        supportChatService.saveMessage(message);
        message.setSender(usersService.getNicknameByEmail(principal.getName()));
        return message;
    }

    @GetMapping("/support")
    public String getSupportPage(Model model, Principal principal) {
        if(usersService.getRoleByEmail(principal.getName()) == 2) {
            model.addAttribute("chats", usersService.getModeratorByEmail(principal.getName())
                    .getChats());
        }
        model.addAttribute("user", usersService.getRoleByEmail(principal.getName()));
        return "support";
    }

    @GetMapping("/chat-support")
    public String getSupportChatPage(Model model, Principal principal,
                                     @RequestParam(name = "chatId", required = false) String chatId) {
        Integer roleOrdinal = usersService.getRoleByEmail(principal.getName());
        if(roleOrdinal != 2) {
            SupportChat supportChat = supportChatService.getChatByUserEmail(principal.getName());
            if(supportChat != null) {
                model.addAttribute("messages", supportChat.getMessages());
                model.addAttribute("moderatorName", supportChat.getModerator().getNickname());
                model.addAttribute("chatId", supportChat.getUuid());
            } else {
                SupportChat newSupportChat = supportChatService.addSupportChatForUser(principal.getName());
                model.addAttribute("messages", newSupportChat.getMessages());
                model.addAttribute("moderatorName", newSupportChat.getModerator().getNickname());
                model.addAttribute("chatId", newSupportChat.getUuid());
            }
        } else {
            SupportChat supportChat = supportChatService.getChatById(chatId);
            model.addAttribute("messages", supportChat.getMessages());
            model.addAttribute("userName", supportChat.getUser().getNickname());
            model.addAttribute("chatId", supportChat.getUuid());
        }
        model.addAttribute("user", usersService.getRoleByEmail(principal.getName()));
        return "chat-support";
    }

    @PostMapping("/support/delete")
    public String deleteChat(@ModelAttribute("chatId")SupportChatIdDto supportChatIdDto) {
        supportChatService.deleteChat(supportChatIdDto.getChatId());

        return "redirect:/support";
    }
}
