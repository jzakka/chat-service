package com.example.chatservice.dto;

import lombok.Data;

@Data
public class ChatDto {
    private String gatherId;
    private String memberId;
    private String content;
}
