package com.example.ludikgames.service;

import com.example.ludikgames.dto.SupportMessageDto;
import com.example.ludikgames.model.SupportChat;

public interface SupportChatService {
    SupportChat getChatByUserEmail(String email);
    SupportChat addSupportChatForUser(String email);

    void saveMessage(SupportMessageDto supportMessageDto);
    SupportChat getChatById(String uuid);

    void deleteChat(String uuid);
}
