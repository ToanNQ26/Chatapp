package com.chat.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.ChatMessageEntity;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity,String> {

}