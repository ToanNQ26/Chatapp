package com.chat.chatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.chatapp.services.EmailService;

@Controller

public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendMail")
   // @ResponseBody
    public String sendMail(@RequestParam String email, @RequestParam String subject) {
        //String content = "Hello word";
        //emailService.sendMail(email, subject, content);
        emailService.sendMailWithThymeleaf(email, subject);
        return "index";
    }

}
