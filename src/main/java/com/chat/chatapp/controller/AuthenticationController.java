package com.chat.chatapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.chat.chatapp.dto.request.AuthenticationRequest;
import com.chat.chatapp.dto.request.IntrospectRequest;
import com.chat.chatapp.dto.response.ApiResponse;
import com.chat.chatapp.dto.response.AuthenticationResponse;
import com.chat.chatapp.dto.response.IntrospectResponse;
import com.chat.chatapp.services.AuthenticationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


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
}
