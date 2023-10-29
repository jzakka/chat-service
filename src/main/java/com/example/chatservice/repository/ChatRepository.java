package com.example.chatservice.repository;

import com.example.chatservice.document.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
}
