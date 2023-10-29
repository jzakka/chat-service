package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.vo.ResponseChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ModelMapper mapper;

    @KafkaListener(topics = "${spring.kafka.topic-name}", containerGroup = "${spring.kafka.consumer.group-id}")
    public void shareMessage(@Payload ChatDto chatDto) {
        ResponseChat responseChat = mapper.map(chatDto, ResponseChat.class);
        messagingTemplate.convertAndSend("/sub/chats/"+chatDto.getGatherId(), responseChat);
    }
}
