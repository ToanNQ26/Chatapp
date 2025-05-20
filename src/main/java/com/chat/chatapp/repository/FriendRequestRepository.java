package com.chat.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long>{
    Optional<FriendRequest> findBySenderIdAndReceiverId(String senderId, String receiverId);
    List<FriendRequest> findBySenderId(String senderId);
    List<FriendRequest> findByReceiverId(String ReceiverId);
}
