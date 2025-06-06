package com.chat.chatapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.chatapp.entity.PasswordResetToken;
import com.chat.chatapp.entity.User;



public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long>{

    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
} 