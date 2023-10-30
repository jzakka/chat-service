package com.example.chatservice.client;

import com.example.chatservice.vo.ResponseJoin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("join-service")
public interface JoinServiceClient {
    @GetMapping("/{gatherId}/joins")
    List<ResponseJoin> getJoins(@PathVariable String gatherId);
}
