package com.example.chatservice.document;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chats")
public class Chat {
    @Id
    private String id;
    private String gatherId;
    private String memberId;
    private String content;
    private LocalDateTime createAt;
}
