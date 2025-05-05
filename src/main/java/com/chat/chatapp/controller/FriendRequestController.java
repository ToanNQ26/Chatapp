package com.chat.chatapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.chat.chatapp.dto.request.CancleFriendRequest;
import com.chat.chatapp.dto.request.SendFriendRequest;
import com.chat.chatapp.dto.response.ApiResponse;
import com.chat.chatapp.entity.FriendRequest;
import com.chat.chatapp.services.FriendRequestService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("friend-requests")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    // Gửi lời mời bạn bè
    @PostMapping()
    public ApiResponse<FriendRequest> sendFriendRequest(@RequestBody SendFriendRequest request) {
        return ApiResponse.<FriendRequest>builder()
                        .message("Successly!")
                        .result(friendRequestService.sendFriendRequest(request))
                        .build();
    }

    // Hủy lời mời bạn bè
    @DeleteMapping("/cancel")
    public ApiResponse<String> cancleFriendRequest(@RequestBody CancleFriendRequest request) {

        return ApiResponse.<String>builder()
                        .result(friendRequestService.cancleFriendRequest(request))
                        .build();
    }

    // Chấp nhận lời mời bạn bè
    @PostMapping("/accept")
    public ApiResponse<Boolean> acceptFriendRequest(@RequestBody SendFriendRequest request) {
        return ApiResponse.<Boolean>builder()
                        .result(friendRequestService.acceptFriendRequest(request))
                        .build();
    }

    // Lấy danh sách lời mời đã nhận
    @GetMapping("/invitations/{userId}")
    public ApiResponse<List<FriendRequest>> getInvitationList(@PathVariable String userId) {
        List<FriendRequest> invitations = friendRequestService.getInvitationList(userId);
        return ApiResponse.<List<FriendRequest>>builder()
                        .result(invitations)
                        .build();
    }

    // Lấy danh sách lời mời đã gửi
    @GetMapping("/sent/{userId}")
    public ApiResponse<List<FriendRequest>> getSentFriendRequests(@PathVariable String userId) {
        List<FriendRequest> sentRequests = friendRequestService.getSentFriendRequests(userId);
        return ApiResponse.<List<FriendRequest>>builder()
                        .result(sentRequests)
                        .build();
    }
}
