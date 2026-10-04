package com.example.policygate.model.response;

import java.util.List;

public record UserResponse(
    String name,
    String email,
    List<RoleResponse> roles
) {}
