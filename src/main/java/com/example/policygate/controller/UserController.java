package com.example.policygate.controller;

import com.example.policygate.model.response.UserResponse;
import com.example.policygate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public UserResponse assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        return userService.assignRoleToUser(userId,roleId);
    }
    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public UserResponse removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        return userService.removeRoleFromUser(userId,roleId);
    }
}
