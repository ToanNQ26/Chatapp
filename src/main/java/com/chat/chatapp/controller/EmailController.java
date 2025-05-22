package com.chat.chatapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.chat.chatapp.dto.request.ForgotPasswordRequest;
import com.chat.chatapp.services.EmailService;
import com.chat.chatapp.services.PasswordResetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {

    
     EmailService emailService;
     PasswordResetService passwordResetService;

    @GetMapping("/sendMail")
   // @ResponseBody
    public String sendMail(@RequestParam String email, @RequestParam String subject) {
        //String content = "Hello word";
        //emailService.sendMail(email, subject, content);
        emailService.sendMailWithThymeleaf(email, subject);
        return "index";
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        
        passwordResetService.sendResetLink(request.getEmail());
        return ResponseEntity.ok("Email đặt lại mật khẩu đã được gửi");
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam String token, Model model) {
        String result = passwordResetService.resetPassword(token);
        model.addAttribute("passwordResult", result);
        return "index"; 
    }

}
