package com.chat.chatapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.repository.UserRepository;
import com.chat.chatapp.security.CurrentUserProvider;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    UserRepository userRepository;
    CurrentUserProvider currentUserProvider;

    private static final String UPLOAD_DIR = "uploads/";

    public String saveImage(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = currentUserProvider.getUserId() + extension;
        Path filepath = Paths.get(UPLOAD_DIR, filename);
       
        Files.createDirectories(filepath.getParent());
        var user = userRepository.findById(currentUserProvider.getUserId())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setAvatar(filename);

        userRepository.save(user);

        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        return "images/" + filename;  
    }
}
