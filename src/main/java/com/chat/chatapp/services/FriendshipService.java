package com.chat.chatapp.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.dto.request.FriendDelete;
import com.chat.chatapp.entity.Friendship;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.repository.FriendshipRepository;
import com.chat.chatapp.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendshipService {
    FriendshipRepository friendshipRepository;
    UserRepository userRepository;

    public String insertSampleFriendships() {
    List<String> users = List.of(
        "user1", "user2", "user3", "user4", "user5", "user6"
    );

    // Mỗi cặp bạn bè là hai chiều: A-B và B-A
    addtofriend(users.get(0), users.get(1)); // user1 <-> user2
    addtofriend(users.get(2), users.get(3)); // user3 <-> user4
    addtofriend(users.get(4), users.get(5)); // user5 <-> user6
    addtofriend(users.get(0), users.get(2)); // user1 <-> user3
    addtofriend(users.get(1), users.get(5)); // user2 <-> user6
    return "Finally!";
    }

    public boolean addtofriend(String userId, String friendId) {

        if(friendshipRepository.findByUserIdAndFriendId(userId, friendId).isPresent())
            throw new AppException(ErrorCode.FRIENDSHIP_EXISTED);
    
        

        Friendship friendship = Friendship.builder()
                                            .userId(userId)
                                            .friendId(friendId)
                                            .createdAt(LocalDate.now())
                                            .build();
        
        Friendship friendship1 = Friendship.builder()
                                            .userId(friendId)
                                            .friendId(userId)
                                            .createdAt(LocalDate.now())
                                            .build();

        friendshipRepository.save(friendship);
        friendshipRepository.save(friendship1);
        return true;
    }

    public List<Friendship> getAllFriendship() {
        return friendshipRepository.findAll();
    }

    public List<User> getListFriends(String userId) {
        List<String> friendIds = friendshipRepository.findByUserId(userId).stream()
        .map(Friendship::getFriendId)
        .toList();
        
        if(friendIds.isEmpty())
            throw new AppException(ErrorCode.USER_HAS_NO_FRIENDS);

        var listUser = userRepository.findAllById(friendIds);
        return listUser;
    }

    public String deleteFriendship(FriendDelete request) {
        var friendship = friendshipRepository.findByUserIdAndFriendId(request.getUserId(),request.getFriendId())
        .orElseThrow(() -> new AppException(ErrorCode.UNICATEGORIZED_EXCEPTION));
        friendshipRepository.delete(friendship);

        var friendship1 = friendshipRepository.findByUserIdAndFriendId(request.getFriendId(), request.getUserId())
        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        friendshipRepository.delete(friendship1);
        
        return "Delete Success!";
    }

}
