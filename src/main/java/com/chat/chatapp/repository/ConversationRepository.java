package com.chat.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.chatapp.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, String>{

   @Query(value = """
    SELECT c.*
    FROM conversation c
    JOIN conversationparticipant cp ON c.conversation_id = cp.conversation_id
    GROUP BY c.conversation_id
    HAVING COUNT(cp.id) > :optional
    """, nativeQuery = true)
    List<Conversation> findConversationsWithParticipantsGreaterThan(@Param("optional") int optional); 
}
