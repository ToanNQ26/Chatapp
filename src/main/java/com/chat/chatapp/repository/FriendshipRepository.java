package com.chat.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.Friendship;

import java.util.List;
import java.util.Optional;


public interface FriendshipRepository extends JpaRepository<Friendship, Long>{
    List<Friendship> findByUserId(String userId);
    Optional<Friendship> findByUserIdAndFriendId(String userId, String friendId);
} 