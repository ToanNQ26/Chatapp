package com.chat.chatapp.services;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.entity.PasswordResetToken;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.repository.PasswordResetTokenRepository;
import com.chat.chatapp.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetService {

    PasswordResetTokenRepository tokenRepo;
    EmailService emailService;
    UserRepository userRepository;


    public void sendResetLink(String email) {

        User user = userRepository.findByemail(email)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));


        String token = UUID.randomUUID().toString();

        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setUser(user);
        prt.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepo.save(prt);

        String link = "http://localhost:8080/home/auth/reset-password?token=" + token;
        String subject = "Reset your password";
        String body = "Click the following link to reset your password:\n" + link;

        emailService.sendMail(user.getEmail(), subject, body);
    }

    public String resetPassword(String token) {
        PasswordResetToken prt = tokenRepo.findByToken(token)
        .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_EXISTED));

        if (prt.getExpiryDate().isBefore(LocalDateTime.now())) 
                return "Token đã hết hạn";

        Random random = new Random();
        // int pass = random.nextInt(10);

        StringBuilder pass = new StringBuilder();
        for(int i = 0; i < 8; i++) {
                pass.append(random.nextInt(10));
        }
        String password = pass + "";
        User user = prt.getUser();      

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(password));      

        tokenRepo.delete(prt);
        return "Your new password: " + password;
    }
}
