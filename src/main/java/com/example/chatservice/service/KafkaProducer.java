package com.example.chatservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Environment env;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> void produceEvent(T event){
        String jsonInString = objectMapper.writeValueAsString(event);

        kafkaTemplate.send(env.getProperty("spring.kafka.topic-name"), jsonInString);
    }
}
