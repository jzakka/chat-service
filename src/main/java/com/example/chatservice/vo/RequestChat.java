package com.example.chatservice.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestChat {
    @NotNull
    private String gatherId;
    @NotNull
    private String memberId;
    @NotNull
    private String content;
}
