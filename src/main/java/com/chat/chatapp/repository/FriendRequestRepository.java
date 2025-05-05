package com.chat.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long>{
    Optional<FriendRequest> findBySenderidAndReceiverid(String senderId, String receiverId);
    List<FriendRequest> findBySenderid(String senderId);
    List<FriendRequest> findByReceiverid(String ReceiverId);
}
