package com.example.ludikgames.service.impl;

import com.example.ludikgames.dto.MessageDto;
import com.example.ludikgames.model.ChatMessage;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.MessageRepository;
import com.example.ludikgames.repository.UsersRepository;
import com.example.ludikgames.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final UsersRepository usersRepository;

    @Override
    public MessageDto saveAndReturnMessageForChat(MessageDto messageDto) {
        Date date = new Date();
        String time = new SimpleDateFormat("HH:mm dd.MM").format(date);

        User user = usersRepository.findByEmail(messageDto.getFrom()).orElseThrow();

        ChatMessage message = ChatMessage.builder()
                .message(messageDto.getText())
                .user(user)
                .dateTime(Instant.from(date.toInstant()))
                .build();

        messageRepository.save(message);

        return MessageDto.builder()
                .from(user.getNickname())
                .text(messageDto.getText())
                .time(time)
                .build();
    }

    @Override
    public List<MessageDto> getMessages() {
        List<ChatMessage> messages = messageRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM")
                .withZone(ZoneId.systemDefault());

        return messages.stream().map(x -> MessageDto.builder()
                .from(x.getUser().getNickname())
                .time(formatter.format(x.getDateTime()))
                .text(x.getMessage())
                .build()).collect(Collectors.toList());
    }
}
