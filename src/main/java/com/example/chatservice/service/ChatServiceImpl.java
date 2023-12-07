package com.example.chatservice.service;

import com.example.chatservice.document.Chat;
import com.example.chatservice.dto.ChatDto;
import com.example.chatservice.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final ModelMapper mapper;
    private final Environment env;
    private final GenerateSequenceService sequenceService;

    @Override
    public ChatDto send(ChatDto chatDto) {
        log.info("chatSerive.send()");
        Chat chat = mapper.map(chatDto, Chat.class);

        Long seq = sequenceService.generateSequence(chatDto.getGatherId());
        chat.setSequence(seq);

        Chat save = chatRepository.save(chat);

        return mapper.map(save, ChatDto.class);
    }

    @Override
    public List<ChatDto> getChats(String gatherId, Long sequence) {
        int size = Integer.parseInt(env.getProperty("page.size"));
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "sequence"));
        List<Chat> chats = chatRepository.findByGatherIdOrderBySequence(gatherId, sequence, pageable);

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
