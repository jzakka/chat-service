package com.example.chatservice.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Document(collection = "chats")
public class Chat {
    @MongoId
    private String id;
    private String gatherId;
    private String memberId;
    private String content;
    private LocalDateTime createAt;
}
