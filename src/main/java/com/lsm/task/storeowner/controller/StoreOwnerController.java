package com.lsm.task.storeowner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.common.dto.ApiResponse;
import com.lsm.task.storeowner.dto.SignUpRequest;
import com.lsm.task.storeowner.service.StoreOwnerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "StoreOwner", description = "사장 API Document")
public class StoreOwnerController {
    private final StoreOwnerService storeOwnerService;

    @Operation(summary = "회원가입", description = "사장님이 회원가입을 한다.")
    @PostMapping("/api/auth/signup")
    public ResponseEntity<ApiResponse> singup(@Valid @RequestBody SignUpRequest request) {
        storeOwnerService.signup(request);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(null));
    }
}
