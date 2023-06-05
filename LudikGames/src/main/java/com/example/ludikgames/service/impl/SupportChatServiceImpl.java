package com.example.ludikgames.service.impl;

import com.example.ludikgames.dto.SupportMessageDto;
import com.example.ludikgames.model.SupportChat;
import com.example.ludikgames.model.SupportMessage;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.SupportChatCustomRepository;
import com.example.ludikgames.repository.SupportChatRepository;
import com.example.ludikgames.repository.SupportMessageRepository;
import com.example.ludikgames.repository.UsersRepository;
import com.example.ludikgames.service.SupportChatService;
import com.example.ludikgames.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupportChatServiceImpl implements SupportChatService {
    private final SupportChatRepository supportChatRepository;
    private final UsersRepository usersRepository;
    private final SupportChatCustomRepository supportChatCustomRepository;

    private final SupportMessageRepository supportMessageRepository;

    private final UsersService usersService;

    @Override
    public SupportChat getChatByUserEmail(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();
        List<SupportChat> supportChat = supportChatCustomRepository.findChatByUser(user);
        if(supportChat.isEmpty()) {
            return null;
        }

        return supportChat.get(0);
    }

    @Override
    public SupportChat addSupportChatForUser(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();
        User moderator = usersService.getModeratorWithMinimumChats();
        SupportChat supportChat = SupportChat.builder()
                .user(user)
                .moderator(moderator)
                .messages(new ArrayList<>())
                .build();

        return supportChatRepository.save(supportChat);
    }

    @Override
    public void saveMessage(SupportMessageDto supportMessageDto) {
        UUID chatUUID = UUID.fromString(supportMessageDto.getUuid());
        SupportChat supportChat = supportChatRepository.findById(chatUUID).orElseThrow();
        User user = usersRepository.findByEmail(supportMessageDto.getSender()).orElseThrow();
        supportMessageRepository.save(SupportMessage.builder()
                .message(supportMessageDto.getContent())
                .user(user)
                .supportChat(supportChat)
                .build());
    }

    @Override
    public SupportChat getChatById(String uuid) {
        UUID chatId = UUID.fromString(uuid);
        return supportChatRepository.findById(chatId).orElseThrow();
    }

    @Override
    public void deleteChat(String uuid) {
        UUID chatId = UUID.fromString(uuid);
        supportChatRepository.deleteById(chatId);
    }
}
