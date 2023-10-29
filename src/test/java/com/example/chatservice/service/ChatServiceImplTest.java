package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.List;

@Slf4j
@SpringBootTest
class ChatServiceImplTest {
    @Autowired
    ChatService chatService;
    @Autowired
    GenerateSequenceService sequenceService;
    @Autowired
    Environment env;

    @BeforeEach
    void clearBefore() {
        chatService.clearAllOnlyForTest();
        sequenceService.deleteAllOnlyForTest();
    }

    @AfterEach
    void clearAfter() {
        chatService.clearAllOnlyForTest();
        sequenceService.deleteAllOnlyForTest();
    }

    @AfterTestClass
    void clear() {
        chatService.clearAllOnlyForTest();
        sequenceService.deleteAllOnlyForTest();
    }

    @Test
    @DisplayName("채팅 전송")
    void send() {
        ChatDto chatDto = new ChatDto("test-gather-id", "test-member-id", "테스트메시지", null, null);

        ChatDto send = chatService.send(chatDto);

        log.info("sent chat = {}", send);
    }

    @Test
    @DisplayName("커서 기반 페이지네이션")
    void pagination() {
        final int CHAT_SIZE = Integer.parseInt(env.getProperty("page.size"));
        final int CHAT_COUNT = 100;
        final String GATHER_ID = "test-gather-id";

        /*
        ...
        test-member-id96 : 테스트 메시지96
        test-member-id97 : 테스트 메시지97
        test-member-id98 : 테스트 메시지98
        test-member-id99 : 테스트 메시지99
         */
        for (int i = 0; i < CHAT_COUNT; i++) {
            ChatDto chatDto = new ChatDto(GATHER_ID, "test-member-id" + i, "테스트 메시지" + i, null, null);

            chatService.send(chatDto);
        }

        List<ChatDto> chats = chatService.getChats(GATHER_ID, Long.MAX_VALUE);
        Assertions.assertThat(chats.get(0).getContent()).isEqualTo("테스트 메시지99");

        chats = chatService.getChats(GATHER_ID, chats.get(CHAT_SIZE - 1).getSequence());
        Assertions.assertThat(chats.get(0).getContent()).isEqualTo("테스트 메시지79");

        chats = chatService.getChats(GATHER_ID, chats.get(CHAT_SIZE - 1).getSequence());
        Assertions.assertThat(chats.get(0).getContent()).isEqualTo("테스트 메시지59");

        chats = chatService.getChats(GATHER_ID, chats.get(CHAT_SIZE - 1).getSequence());
        Assertions.assertThat(chats.get(0).getContent()).isEqualTo("테스트 메시지39");

        chats = chatService.getChats(GATHER_ID, chats.get(CHAT_SIZE - 1).getSequence());
        Assertions.assertThat(chats.get(0).getContent()).isEqualTo("테스트 메시지19");

        chats = chatService.getChats(GATHER_ID, chats.get(9).getSequence());
        Assertions.assertThat(chats.size()).isEqualTo(10);
    }
}