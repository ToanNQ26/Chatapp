package com.chat.chatapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
  
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendMail(String to,String subject,String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // cái này và cái dưới đều gửi nhiều người cùng lúc được, tham số là 1 mảng
        //message.setBcc(to); //sử dụng cái này để gửi khi không muốn tiết lộ mail của nhau
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public void sendMailWithThymeleaf(String to,String subject) {
        Context context = new Context();
        context.setVariable("name", to);
        String htmlContent = templateEngine.process("index", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");
        try {
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent,true);
            mailSender.send(message);
        } catch(MessagingException e) {
            e.printStackTrace();
        }
    }

}
