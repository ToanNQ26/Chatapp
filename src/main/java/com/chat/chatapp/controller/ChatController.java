package com.chat.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.chatapp.dto.request.MessageRequest;
import com.chat.chatapp.model.ChatMessage;
import com.chat.chatapp.services.ChatService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ChatController {

    ChatService chatService;
    SimpMessagingTemplate simpMessagingTemplate;

  

    @MessageMapping("/sendMessage")
    public void sendToGroup( MessageRequest message) {
        System.out.println("Sending message to group: " + message.getConversationId().getConversationId());
        System.out.println("Receive: " + message.getContent());
        //chatService.saveMessage(message);
        chatService.saveChatMessage(message);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getConversationId().getConversationId(), message); //chatService.saveMessage(message));
    }

    
    // @MessageMapping("/sendMessage")
    // @SendTo("/topic/messages")
    // public ChatMessage sendMessage(ChatMessage message) {
    //     System.out.println("Received: " + message);
    //     chatService.saveChatMessage(message);
    //     return message;
    // }


    @MessageMapping("/leave")
    @SendTo("/topic/messages")
    public ChatMessage leave(String username) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setContent(username + " đã rời phòng chat");
        newMessage.setSender(username);
        newMessage.setType("LEAVE");
        
        return newMessage;
    }

    @MessageMapping("/join")
    @SendTo("/topic/messages")
    public ChatMessage join(String username) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setContent(username + " đã tham gia phòng chat");
        newMessage.setSender(username);
        newMessage.setType("JOIN");
        
        return newMessage;
    }
}
