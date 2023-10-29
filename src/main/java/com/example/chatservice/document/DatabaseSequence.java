package com.example.chatservice.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class DatabaseSequence {
    private String gatherId;
    private long sequence;
}
