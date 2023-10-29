package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {
    ChatDto send(ChatDto chatDto);

    /**
     * gatherId의 모임의 채팅 중에서 sequence보다 순서가 작은 최신의 채팅을
     * 정해진 수량만큼 가져온다.
     *
     * @param gatherId
     * @param sequence
     * @return
     */
    List<ChatDto> getChats(String gatherId, Long sequence);

    void clearAllOnlyForTest();
}
