package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceImplTest {
    @Autowired
    ChatService chatService;

    @BeforeEach
    void clearBefore() {
        chatService.clearAllOnlyForTest();
    }

    @AfterEach
    void clearAfter() {
        chatService.clearAllOnlyForTest();
    }

    @Test
    @DisplayName("채팅 전송")
    void send() {
        ChatDto chatDto = new ChatDto("test-gather-id", "test-member-id", "테스트메시지", null);

        chatService.send(chatDto);
    }


}