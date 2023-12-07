package com.example.chatservice.interceptor;

import com.example.chatservice.utils.JwtUtils;
import com.example.chatservice.vo.TokenJoinAuthority;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.AuthorizationException;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomChannelInterceptor implements ChannelInterceptor {
    private final JwtUtils jwtUtils;
    private final Environment env;

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
        List<TokenJoinAuthority> joinAuthorities = jwtUtils.getJoinAuthorities(jwtToken);

        String gatherId = null;
        String path = accessor.getNativeHeader("destination").get(0);
        if (path != null) {
            gatherId = getGatherIdFromPath(path);
        }

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            return subscribeMessage(message, gatherId, joinAuthorities);
        }

        return message;
    }

    private String getGatherIdFromPath(String path) {
        String gatherId;
        String[] segments = path.split("/");
        gatherId = segments[segments.length - 1];
        return gatherId;
    }

    private Message<?> subscribeMessage(Message<?> message,
                                        String gatherId,
                                        List<TokenJoinAuthority> joinAuthorities) {
        List<String> gatherIds = joinAuthorities.stream()
                .map(TokenJoinAuthority::getGatherId)
                .toList();

        if (gatherIds.contains(gatherId)) {
            return message;
        }

        throw new AuthorizationException(env.getProperty("token.invalid-msg"));
    }
}
