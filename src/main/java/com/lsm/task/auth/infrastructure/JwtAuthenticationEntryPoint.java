package com.lsm.task.auth.infrastructure;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsm.task.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        // AuthJwtFilter의 Exception에서 설정한 메시지를 가져옴
        String errorMessage = (String) request.getAttribute("jwtExceptionMessage");
        sendErrorResponse(response, errorMessage);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        ApiResponse apiResponse = ApiResponse.ofErrorResponse(HttpStatus.UNAUTHORIZED.value(), message, null);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
