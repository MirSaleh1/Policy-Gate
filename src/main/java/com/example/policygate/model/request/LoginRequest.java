package com.example.policygate.model.request;

public record LoginRequest(
    String email,
    String password
) {}
