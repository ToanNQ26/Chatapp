package com.chat.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.chatapp.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, String>{

//    @Query(value = """
//     SELECT c.*
//     FROM conversation c
//     JOIN conversationparticipant cp ON c.conversation_id = cp.conversation_id
//     GROUP BY c.conversation_id
//     HAVING COUNT(cp.id) > :optional
//     """, nativeQuery = true)
//     List<Conversation> findConversationsWithParticipantsGreaterThan(@Param("optional") int optional); 

    @Query(value = """
    SELECT c.*
    FROM conversation c
    JOIN conversationparticipant cp ON c.conversation_id = cp.conversation_id
    WHERE c.conversation_id IN (
        SELECT cp2.conversation_id
        FROM conversationparticipant cp2
        GROUP BY cp2.conversation_id
        HAVING COUNT(cp2.id) > :optional
    )
    AND cp.user_id = :userId
    """, nativeQuery = true)
    List<Conversation> findConversationsWithParticipantsGreaterThanAndUserJoined(
    @Param("optional") int optional,
    @Param("userId") String userId
    );
}
