package com.example.policygate.model.response;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
}
