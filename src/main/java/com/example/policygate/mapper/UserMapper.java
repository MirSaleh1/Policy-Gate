package com.example.policygate.mapper;

import com.example.policygate.entity.User;
import com.example.policygate.model.response.UserResponse;

public interface UserMapper {

    static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUsername(), user.getEmail(),
                user.getRoles().stream().map(RoleMapper::toRoleResponse).toList());
    }
}
