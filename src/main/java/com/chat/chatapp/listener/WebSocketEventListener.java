// package com.chat.chatapp.listener;

// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.messaging.SessionConnectedEvent;
// import org.springframework.web.socket.messaging.SessionDisconnectEvent;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.event.EventListener;

// @Component
// public class WebSocketEventListener {

//     @Autowired
//     private StringRedisTemplate redisTemplate;

//     @EventListener
//     public void handleSessionConnected(SessionConnectedEvent event) {
//         String userId = event.getUser().getName();
//         // Lưu trạng thái online vào Redis
//         redisTemplate.opsForValue().set(userId, "online");
//         System.out.println("\n" + userId + " is online" + "\n");
//     }

//     @EventListener
//     public void handleSessionDisconnect(SessionDisconnectEvent event) {
//         String userId = event.getUser().getName();
//         // Xóa trạng thái khỏi Redis khi người dùng offline
//         redisTemplate.delete(userId);
//         System.out.println(userId + " is offline");
//     }

//     public boolean isUserOnline(String userId) {
//         return redisTemplate.hasKey(userId); // Kiểm tra xem người dùng có trong Redis không
//     }
// }

