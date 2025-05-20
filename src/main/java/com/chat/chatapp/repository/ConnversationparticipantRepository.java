package com.chat.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.Conversationparticipant;

public interface ConnversationparticipantRepository extends JpaRepository<Conversationparticipant,Long> {
   List<Conversationparticipant> findByUserId(String userId);
   List<Conversationparticipant> findByConversationId(String conversationId);
   //Optional<Conversationparticipant> findByuserId(String userId);
   void deleteByUserIdAndConversationId(String userId,String conversationId);
   List<Conversationparticipant> findAllByConversationId(String conversationId);
   Optional<Conversationparticipant> findByUserIdAndConversationId(String userId,String conversationId);
   List<Conversationparticipant> findAllByConversationIdAndUserIdIn(String conversationId, List<String> userIds);
}