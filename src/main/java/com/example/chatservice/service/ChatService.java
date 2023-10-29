package com.example.chatservice.service;

import com.example.chatservice.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {
    ChatDto send(ChatDto chatDto);

    /**
     * gatherId의 모임의 채팅 중에서 olderThan보다 오래된 가장 최신의 채팅을
     * 정해진 수량만큼 가져온다.
     *
     * @param gatherId
     * @param olderThan
     * @return
     */
    List<ChatDto> getChats(String gatherId, LocalDateTime olderThan);

    void clearAllOnlyForTest();
}
