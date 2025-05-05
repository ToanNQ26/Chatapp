package com.chat.chatapp.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chatapp.dto.request.FriendDelete;
import com.chat.chatapp.dto.response.ApiResponse;
import com.chat.chatapp.entity.Friendship;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.services.FriendshipService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendController {

    FriendshipService friendshipService;
    
    @PostMapping
    public ApiResponse<Boolean> addtoFriend(@RequestBody String userId,@RequestBody String friendId) {
        return ApiResponse.<Boolean>builder()
                        .message("Add friend successly!")
                        .result(friendshipService.addtofriend(userId, friendId))
                        .build();
    }

    // @PostMapping
    // public ApiResponse<String> addtoFriend() {
    //     friendshipService.insertSampleFriendships();
    //     return ApiResponse.<String>builder()
    //                     .message("Data example insert sucessly!")
    //                     //.result(friendshipService.insertSampleFriendships());
    //                     .build();
    // }

    @GetMapping
    public ApiResponse<List<Friendship>> getAllFriend() {
        return ApiResponse.<List<Friendship>>builder()
                        .result(friendshipService.getAllFriendship())
                        .build();
    }   

    @GetMapping("/{id}")
    public ApiResponse<List<User>> getListFriend(@PathVariable String id) {
        return ApiResponse.<List<User>>builder()
                        .result(friendshipService.getListFriends(id))
                        .build();
    }  
    
    @DeleteMapping()
    public ApiResponse<String> deleteFriend(@RequestBody FriendDelete request) {
        System.out.println(request.getUserId() + "\n");
        System.out.println(request.getFriendId() + "\n");
        return ApiResponse.<String>builder()
                            .result(friendshipService.deleteFriendship(request))
                            .build();
    }
}
