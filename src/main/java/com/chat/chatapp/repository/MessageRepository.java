package com.chat.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.Conversation;
import com.chat.chatapp.entity.Message;

public interface MessageRepository extends JpaRepository<Message, String>{
        List<Message> findByConversationIdOrderByTimeStampAsc(Conversation conversation);
} 
