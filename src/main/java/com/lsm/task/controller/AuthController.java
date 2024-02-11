package com.lsm.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.dto.ApiResponse;
import com.lsm.task.dto.SignUpRequest;
import com.lsm.task.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<ApiResponse> singup(@Valid @RequestBody SignUpRequest request) {
        authService.signup(request);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(null));
    }
}
