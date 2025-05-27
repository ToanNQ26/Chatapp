package com.chat.chatapp.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.dto.request.UpdatePasswordRequest;
import com.chat.chatapp.dto.request.UserUpdateRequest;
import com.chat.chatapp.dto.request.UsercreationRequest;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.enums.Role;
import com.chat.chatapp.mapper.UserMapper;
import com.chat.chatapp.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServices {

    UserRepository userRepository;
    UserMapper userMapper;

    public User createUser(UsercreationRequest request){

        if(userRepository.findByPhone(request.getPhone()).isPresent())
            throw new AppException(ErrorCode.USER_EXISTED);
        

        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User getUserbyId(String id) {
        return userRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public List<User> getUser() { 
        return userRepository.findAll();
    }

    public User getUser(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(()-> new RuntimeException("User not found!"));
    }

    public String updateUser(String userId, UserUpdateRequest request) { 
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));


        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
            user.setActive(request.isActive());

            userRepository.save(user);
            return "Upate thành công";
    }

    public String deleteUser(String userID) { 
        if (!userRepository.existsById(userID)) {
            return "Không có người dùng sở hữu ID này";
        }
        userRepository.deleteById(userID);
        return "Xóa thành công";
    }

    public boolean updatePassword(UpdatePasswordRequest request) {
        var user = userRepository.findByuserId(request.getUserId())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.save(user);
        if(userRepository.existsById(user.getUserId()))
            return true;
        
        return false;
    }

    public List<User> getUserOnline() {
        var listUser = userRepository.findByIsActive(true);
        if (listUser.isEmpty()) 
            throw new AppException(ErrorCode.NO_USERS_ARE_ONLINE);
        return listUser;
    }

    public void setUserOnline(String userId) {
        var user = userRepository.findByuserId(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setActive(true);
        userRepository.save(user);
    }

    public void setUserOffine(String userId) {
        var user = userRepository.findByuserId(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setActive(false);
        userRepository.save(user);
    }

}
