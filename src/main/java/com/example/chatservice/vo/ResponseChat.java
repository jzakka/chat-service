package com.example.chatservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseChat {
    private String gatherId;
    private String memberId;
    private String content;
    private LocalDateTime createAt;
    private Long sequence;
}
