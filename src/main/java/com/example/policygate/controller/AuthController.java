package com.example.policygate.controller;


import com.example.policygate.model.request.LoginRequest;
import com.example.policygate.model.request.RegisterRequest;
import com.example.policygate.model.request.TokenRequest;
import com.example.policygate.model.response.TokenResponse;
import com.example.policygate.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public TokenResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestBody TokenRequest request) {
        return authService.refreshToken(request);
    }
}
