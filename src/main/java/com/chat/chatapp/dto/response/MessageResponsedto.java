package com.chat.chatapp.dto.response;

import java.util.Date;

import com.chat.chatapp.entity.Conversation;
import com.chat.chatapp.entity.Message;
import com.chat.chatapp.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponsedto {
    private String content;
    private User senderId;
    private Date timeStamp;
    Conversation conversationId;

    public MessageResponsedto(Message message) {
        this.content = message.getContent();
        this.senderId = message.getSenderId(); 
        this.timeStamp = message.getTimeStamp();
        this.conversationId = message.getConversationId();
    }
}