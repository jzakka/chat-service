package com.example.chatservice.repository;

import com.example.chatservice.document.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    @Query("{ 'gatherId': ?0, 'createdAt': { $lt: ?1 } }")
    List<Chat> findByGatherIdAndCreateAtBeforeOrderByCreateAt(String gatherId, LocalDateTime orderThan, Pageable pageable);

}
