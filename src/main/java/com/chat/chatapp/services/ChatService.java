package com.chat.chatapp.services;

import org.springframework.stereotype.Service;

import com.chat.chatapp.dto.request.MessageRequest;
import com.chat.chatapp.entity.Message;
import com.chat.chatapp.repository.MessageRepository;
//import com.chat.chatapp.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
 
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {


    MessageRepository messageRepository;
    //UserRepository userRepository;

    public Message saveChatMessage(MessageRequest message) {
      
      // var user = userRepository.findById(message.getSenderId())
      // .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Message messageSave = Message.builder()
                              .senderId(message.getSenderId())
                              .conversationId(message.getConversationId())
                              .timeStamp(message.getTimeStamp())
                              .content(message.getContent())
                              .build();
        return messageRepository.save(messageSave);
   }

   public String saveMessage(Message message) {

     //   return messageRepository.save(message);
     return "test";
   }

}
