package com.chat.chatapp.dto.request;

import java.util.Date;

import com.chat.chatapp.entity.Conversation;
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
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Builder
public class MessageRequest {
    String content;
    Conversation conversationId;
    User senderId;
    Date timeStamp;
}
