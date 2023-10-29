package com.example.chatservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto implements Serializable {
    private String gatherId;
    private String memberId;
    private String content;
    private LocalDateTime createAt;
    private Long sequence;
}
