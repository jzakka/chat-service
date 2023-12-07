package com.example.chatservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Environment env;
    private final ObjectMapper objectMapper;


    public <T> void produceEvent(T event){
        log.info("kafkaProducer.produceEvent()");
        String jsonInString = convertToString(event);

        kafkaTemplate.send(env.getProperty("spring.kafka.topic-name"), jsonInString);
    }

    private <T> String convertToString(T event) {
        String jsonInString;

        try {
            jsonInString = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Cannot convert event to jsonString");
            throw new RuntimeException(e);
        }
        return jsonInString;
    }
}
