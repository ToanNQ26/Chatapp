package com.chat.chatapp.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.dto.request.CancleFriendRequest;
import com.chat.chatapp.dto.request.SendFriendRequest;
import com.chat.chatapp.entity.FriendRequest;
import com.chat.chatapp.mapper.FriendRequestMapper;
import com.chat.chatapp.repository.FriendRequestRepository;
import com.chat.chatapp.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendRequestService {
    FriendRequestRepository friendRequestRepository;
    FriendRequestMapper friendRequestMapper;
    FriendshipService friendshipService;
    UserRepository uRepository;

    public FriendRequest sendFriendRequest(SendFriendRequest request) {

        uRepository.findById(request.getReceiverid())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        FriendRequest friendRequest = friendRequestMapper.toFriendRequest(request);
        friendRequest.setCreatedAt(LocalDate.now());

        return friendRequestRepository.save(friendRequest);
    }

    public String cancleFriendRequest(CancleFriendRequest request) {
        
        var friendRequest = friendRequestRepository.
        findBySenderidAndReceiverid(request.getSenderId(), request.getReceiverId())
        .orElseThrow(() -> new AppException(ErrorCode.FRIEND_REQUEST_DOES_NOT_EXISTED));

        friendRequestRepository.delete(friendRequest);
        return "Delete Successsly!";
    }

    public Boolean acceptFriendRequest(SendFriendRequest request) {

        var friendRequest = friendRequestRepository.findById(request.getId())
            .orElseThrow(() -> new AppException(ErrorCode.FRIEND_REQUEST_DOES_NOT_EXISTED));

        Boolean checkAccept = friendshipService
        .addtofriend(request.getSenderid(), request.getReceiverid());

        friendRequestRepository.delete(friendRequest);
        return checkAccept;
    }

    public List<FriendRequest> getInvitationList(String userId) {
        
        var friendRequestList = friendRequestRepository.findByReceiverid(userId);
        
        if(friendRequestList.isEmpty()) 
            throw new AppException(ErrorCode.FRIEND_REQ_404);
        
        return friendRequestList;
    }

    public List<FriendRequest> getSentFriendRequests(String userId) {

        var friendRequestList = friendRequestRepository.findBySenderid(userId);

        if(friendRequestList.isEmpty()) 
            throw new AppException(ErrorCode.FRIEND_REQ_404);
        
        return friendRequestList;
    }
}
