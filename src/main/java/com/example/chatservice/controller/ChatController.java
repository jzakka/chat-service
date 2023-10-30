package com.example.chatservice.controller;

import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.service.ChatService;
import com.example.chatservice.utils.JwtUtils;
import com.example.chatservice.vo.RequestChat;
import com.example.chatservice.vo.ResponseChat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Environment env;
    private final JwtUtils jwtUtils;

    @SneakyThrows
    @MessageMapping("/chats/message")
    public void send(@Payload RequestChat chatRequest, @Header("Authorization") String bearerToken) {
        String tokenMemberId = jwtUtils.getMemberId(bearerToken);

        ChatDto chatDto = modelMapper.map(chatRequest, ChatDto.class);
        chatDto.setMemberId(tokenMemberId);

        ChatDto sentChatDto = chatService.send(chatDto);

        String jsonInString = objectMapper.writeValueAsString(sentChatDto);

        kafkaTemplate.send(env.getProperty("spring.kafka.topic-name"), jsonInString);
    }

    @GetMapping("/chats/{gatherId}")
    public ResponseEntity<List<ResponseChat>> getChats(@PathVariable String gatherId, @RequestParam Long seq) {
        List<ChatDto> chats = chatService.getChats(gatherId, seq);

        List<ResponseChat> body = chats.stream()
                .map(chatDto -> modelMapper.map(chatDto, ResponseChat.class))
                .toList();

        return ResponseEntity.ok(body);
    }
}
