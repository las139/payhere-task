package com.lsm.task.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.common.dto.ApiResponse;
import com.lsm.task.auth.dto.LoginRequest;
import com.lsm.task.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "Auth", description = "인증 API Document")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "휴대전화번호와 비밀번호 입력 후 로그인한다.")
    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(authService.login(request)));
    }
}
