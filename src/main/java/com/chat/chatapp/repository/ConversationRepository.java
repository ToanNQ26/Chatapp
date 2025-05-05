package com.chat.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, String>{

    
} 
