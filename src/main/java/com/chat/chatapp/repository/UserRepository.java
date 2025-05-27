package com.chat.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.chatapp.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String>{
    boolean existsByuserId(String userId);
    Optional<User> findByuserId(String userId);
    Optional<User> findByPhone(String phone);
    Optional<User> findByemail(String email);
    List<User> findByIsActive(Boolean active);
}
            