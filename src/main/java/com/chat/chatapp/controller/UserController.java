package com.chat.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chatapp.dto.request.UpdatePasswordRequest;
import com.chat.chatapp.dto.request.UserUpdateRequest;
import com.chat.chatapp.dto.request.UsercreationRequest;
import com.chat.chatapp.dto.response.ApiResponse;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.services.UserServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserServices userServices;
    @PostMapping("/register")
    public User UserRequestRegister(@RequestBody UsercreationRequest request) {
        return userServices.createUser(request); 
    }
    @GetMapping("/getUser/{phone}")
    public User getUser(@PathVariable String phone) {
        return userServices.getUser(phone);
    }
    @GetMapping("/getUser")
    public List<User> getUser() {
        return userServices.getUser();
    }
    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable String id) {
        return userServices.getUserbyId(id);
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        return userServices.updateUser(id, request);
    }
    @PostMapping("/password")
    public ApiResponse<Boolean> updatePassword(@RequestBody UpdatePasswordRequest request) {
        return ApiResponse.<Boolean>builder()
                .result(userServices.updatePassword(request))
                .build();
    }    
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .result(userServices.deleteUser(id))
                .build();
    }
}

