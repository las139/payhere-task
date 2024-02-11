package com.lsm.task.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lsm.task.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ApiResponse.ofErrorResponse(httpStatus.value(), exception.getMessage(), null);
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleAuthorizationException(HttpServletRequest request, AuthorizationException exception) {
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        return ApiResponse.ofErrorResponse(httpStatus.value(), exception.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleException(HttpServletRequest request, Exception exception) throws IOException {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ApiResponse.ofErrorResponse(httpStatus.value(), exception.getMessage(), null);
    }
}
