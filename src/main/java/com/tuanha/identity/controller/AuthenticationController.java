package com.tuanha.identity.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuanha.identity.dto.request.AuthenticationRequest;
import com.tuanha.identity.dto.response.ApiResponse;
import com.tuanha.identity.dto.response.AuthenticationResponse;
import com.tuanha.identity.service.IAuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    IAuthenticationService authenticationService;
    
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean result = authenticationService.authenticated(request);
        return ApiResponse.<AuthenticationResponse>builder()
        .result(AuthenticationResponse.builder()
        .authenticated(result)
        .build())
        .build();
    }

}
