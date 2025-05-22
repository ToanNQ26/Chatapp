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
import com.chat.chatapp.security.CurrentUserProvider;

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
    CurrentUserProvider currentUserProvider;

    public FriendRequest sendFriendRequest(SendFriendRequest request) {

        if(request.getReceiverId().equals(request.getSenderId()))
            throw new AppException(ErrorCode.CAN_NOT_SEND_TO_YOURSELF);

        if(friendshipService.friendExists(request.getSenderId(), request.getReceiverId()))
            throw new AppException(ErrorCode.FRIENDSHIP_EXISTED);

        if(friendRequestRepository.
        findBySenderIdAndReceiverId(request.getSenderId(), request.getReceiverId()).isPresent())
            throw new AppException(ErrorCode.FRIEND_REQUEST_EXISTED);


        uRepository.findById(request.getReceiverId())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        System.out.println("\n S:" + request);

        FriendRequest friendRequest = friendRequestMapper.toFriendRequest(request);
        friendRequest.setCreatedAt(LocalDate.now());

        return friendRequestRepository.save(friendRequest);
    }

    public String cancleFriendRequest(CancleFriendRequest request) {
        
        var friendRequest = friendRequestRepository.
        findBySenderIdAndReceiverId(request.getSenderId(), request.getReceiverId())
        .orElseThrow(() -> new AppException(ErrorCode.FRIEND_REQUEST_DOES_NOT_EXISTED));

        friendRequestRepository.delete(friendRequest);
        return "Delete Successsly!";
    }

    public Boolean acceptFriendRequest(Long id) {

        var friendRequest = friendRequestRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.FRIEND_REQUEST_DOES_NOT_EXISTED));

        String userId = currentUserProvider.getUserId();
        if (!friendRequest.getReceiverId().equals(userId))
            throw new AppException(ErrorCode.USER_UNAUTHORIZED);

        Boolean checkAccept = friendshipService
        .addtofriend(friendRequest.getSenderId(), friendRequest.getReceiverId());

        friendRequestRepository.delete(friendRequest);
        return checkAccept;
    }

    public List<FriendRequest> getInvitationList(String userId) {
        
        var friendRequestList = friendRequestRepository.findByReceiverId(userId);
        
        if(friendRequestList.isEmpty()) 
            throw new AppException(ErrorCode.FRIEND_REQ_404);
        
        return friendRequestList;
    }

    public List<FriendRequest> getSentFriendRequests(String userId) {

        var friendRequestList = friendRequestRepository.findBySenderId(userId);

        if(friendRequestList.isEmpty()) 
            throw new AppException(ErrorCode.FRIEND_REQ_404);
        
        return friendRequestList;
    }
}
