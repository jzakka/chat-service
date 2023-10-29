package com.example.chatservice.controller;

import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.service.ChatService;
import com.example.chatservice.vo.RequestChat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Environment env;

    @SneakyThrows
    @MessageMapping("/chats/message")
    public void send(RequestChat chatRequest) {
        ChatDto chatDto = modelMapper.map(chatRequest, ChatDto.class);
        ChatDto sentChatDto = chatService.send(chatDto);

        String jsonInString = objectMapper.writeValueAsString(sentChatDto);

        kafkaTemplate.send(env.getProperty("spring.kafka.topic-name"), jsonInString);
    }
}
