package com.example.chatservice.service;

import com.example.chatservice.document.Chat;
import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ModelMapper mapper;
    private final Environment env;

    @Override
    public ChatDto send(ChatDto chatDto) {
        Chat chat = mapper.map(chatDto, Chat.class);
        chatRepository.save(chat);

        return chatDto;
    }

    @Override
    public List<ChatDto> getChats(String gatherId, LocalDateTime olderThan) {
        int size = Integer.parseInt(env.getProperty("page.size"));
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createAt"));
        List<Chat> chats = chatRepository.findByGatherIdAndCreateAtBeforeOrderByCreateAt(gatherId, olderThan, pageable);

        List<ChatDto> chatDtos = chats.stream()
                .map(document -> mapper.map(document, ChatDto.class))
                .toList();

        return chatDtos;
    }

    @Override
    public void clearAllOnlyForTest() {
        chatRepository.deleteAll();
    }
}
