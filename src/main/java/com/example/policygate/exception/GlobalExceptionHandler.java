package com.example.policygate.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ExceptionResponse handleBusinessException(BusinessException ex, HttpServletResponse response) {
        response.setStatus(ex.getExceptionConstants().getStatus().value());
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ExceptionResponse handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return new ExceptionResponse("Sizin bu emeliyyati yerine yetirmek ucun icazeniz yoxdur! (Access Denied)");
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleGeneralException(Exception ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ExceptionResponse("Daxili server xətası baş verdi: " + ex.getMessage());
    }
}
