package com.chat.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.chatapp.entity.Conversationparticipant;

public interface ConnversationparticipantRepository extends JpaRepository<Conversationparticipant,Long> {
   List<Conversationparticipant> findByUserId(String userId);
   List<Conversationparticipant> findByConversationId(String conversationId);
   //Optional<Conversationparticipant> findByuserId(String userId);
   void deleteByUserIdAndConversationId(String userId,String conversationId);
   List<Conversationparticipant> findAllByConversationId(String conversationId);
   Optional<Conversationparticipant> findByUserIdAndConversationId(String userId,String conversationId);
   List<Conversationparticipant> findAllByConversationIdAndUserIdIn(String conversationId, List<String> userIds);

   @Query(value = """
    SELECT cp.conversation_id
    FROM conversationparticipant cp
    GROUP BY cp.conversation_id
    HAVING 
        COUNT(*) = :size 
        AND COUNT(CASE WHEN cp.user_id IN (:userIds) THEN 1 END) = :size
    """, nativeQuery = true)
   List<String> findConversationIdsByExactParticipants(@Param("userIds") List<String> userIds, @Param("size") long size);


}