package com.example.policygate.model.request;

public record RegisterRequest(
    String name,
    String email,
    String password
) {}
