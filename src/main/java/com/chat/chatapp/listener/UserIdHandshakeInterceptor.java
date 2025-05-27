package com.chat.chatapp.listener;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class UserIdHandshakeInterceptor implements HandshakeInterceptor {

    @Override
public boolean beforeHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Map<String, Object> attributes) throws Exception {

    var query = request.getURI().getQuery(); 
    System.out.println("Id nhan duoc: " + query.split("userId=")[1]);


    if (query != null && query.contains("userId=")) {
        var userId = query.split("userId=")[1];
        attributes.put("userId", userId); 

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            servletRequest.getServletRequest().getSession().setAttribute("userId", userId); 
        }
    }
        return true;
    }


    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // nothing
    }
}

