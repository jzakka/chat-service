package com.example.chatservice.interceptor;

import com.example.chatservice.client.JoinServiceClient;
import com.example.chatservice.utils.JwtUtils;
import com.example.chatservice.vo.ResponseJoin;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.errors.AuthorizationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomChannelInterceptor implements ChannelInterceptor {
    private final JwtUtils jwtUtils;
    private final JoinServiceClient joinServiceClient;
    private final Environment env;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (!StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            return message;
        }

        List<String> authorization = accessor.getNativeHeader("Authorization");

        if (authorization == null || authorization.isEmpty()) {
            throw new AuthorizationException(env.getProperty("token.invalid-msg"));
        }

        String jwtToken = authorization.get(0).replace("Bearer ", "");
        String memberId = jwtUtils.getMemberId(jwtToken);

        String gatherId = null;
        String path = accessor.getNativeHeader("destination").get(0);
        if (path != null) {
            gatherId = getGatherIdFromPath(path);
        }

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            return subscribeMessage(message, gatherId, memberId);
        }

        return message;
    }

    private String getGatherIdFromPath(String path) {
        String gatherId;
        String[] segments = path.split("/");
        gatherId = segments[segments.length - 1];
        return gatherId;
    }

    private Message<?> subscribeMessage(Message<?> message, String gatherId, String memberId) {
        List<String> memberIds = joinServiceClient.getJoins(gatherId).stream()
                .map(ResponseJoin::getMemberId)
                .toList();

        if (memberIds.contains(memberId)) {
            return message;
        }

        throw new AuthorizationException(env.getProperty("token.invalid-msg"));
    }
}
