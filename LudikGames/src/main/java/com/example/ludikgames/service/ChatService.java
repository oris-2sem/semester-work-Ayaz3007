package com.example.ludikgames.service;

import com.example.ludikgames.dto.MessageDto;

import java.util.List;

public interface ChatService {
    MessageDto saveAndReturnMessageForChat(MessageDto messageDto);
    List<MessageDto> getMessages();
}
