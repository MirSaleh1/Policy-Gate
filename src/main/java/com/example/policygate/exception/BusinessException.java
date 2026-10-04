package com.example.policygate.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    
    private final ExceptionConstants exceptionConstants;

    public BusinessException(ExceptionConstants exceptionConstants) {
        super(exceptionConstants.getMessage());
        this.exceptionConstants = exceptionConstants;
    }
}
