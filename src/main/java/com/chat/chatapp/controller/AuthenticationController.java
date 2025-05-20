package com.chat.chatapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chatapp.dto.request.AuthenticationRequest;
import com.chat.chatapp.dto.request.IntrospectRequest;
import com.chat.chatapp.dto.response.ApiResponse;
import com.chat.chatapp.dto.response.AuthenticationResponse;
import com.chat.chatapp.dto.response.IntrospectResponse;
import com.chat.chatapp.services.AuthenticationService;
import com.chat.chatapp.services.PasswordResetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

      AuthenticationService authenticationService;
      PasswordResetService passResetService;


    @PostMapping("/token")
    ApiResponse<AuthenticationResponse>  authenticate(@RequestBody AuthenticationRequest request) { 

    var result = authenticationService.authenticate((request));

    return ApiResponse.<AuthenticationResponse>builder()
            .result(result)
            .build();
    } 

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse>  introspectrequest(@RequestBody IntrospectRequest request) { 

    var result = authenticationService.introspectResponse((request));

    return ApiResponse.<IntrospectResponse>builder()
            .result(result)
            .build();
    }

    @GetMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestParam String token) {
        return ApiResponse.<String>builder()
        .result(passResetService.resetPassword(token))
        .build();
        }

        
}
