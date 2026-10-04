package com.example.policygate.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstants {
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("User already exists", HttpStatus.CONFLICT),
    USER_CANNOT_BE_NULL("User cannot be null", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN("Invalid refresh token", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXISTS("Role already exists", HttpStatus.CONFLICT),
    PERMISSION_NOT_FOUND("Permission not found", HttpStatus.NOT_FOUND),
    PERMISSION_ALREADY_EXISTS("Permission already exists", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;
}
