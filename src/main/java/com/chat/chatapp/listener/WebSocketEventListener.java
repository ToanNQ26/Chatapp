package com.chat.chatapp.listener;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.services.UserServices;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal= true)
public class WebSocketEventListener {

    UserServices userService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = accessor.getFirstNativeHeader("userId"); // <-- lấy từ connectHeaders
        System.out.println("User ID from STOMP header: " + userId);

        if (userId == null) {
        throw new AppException(ErrorCode.UNICATEGORIZED_EXCEPTION);
        }

        accessor.getSessionAttributes().put("userId", userId);

        userService.setUserOnline(userId);
    }



    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String userId = extractUsername(event);
        
       userService.setUserOffine(userId); 
    }


    private String extractUsername(AbstractSubProtocolEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes == null || sessionAttributes.get("userId") == null) {
            throw new AppException(ErrorCode.UNICATEGORIZED_EXCEPTION);
        }

        return sessionAttributes.get("userId").toString();
    }
}
