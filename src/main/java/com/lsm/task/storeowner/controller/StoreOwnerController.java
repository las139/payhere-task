package com.lsm.task.storeowner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.common.dto.ApiResponse;
import com.lsm.task.storeowner.dto.SignUpRequest;
import com.lsm.task.storeowner.service.StoreOwnerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class StoreOwnerController {
    private final StoreOwnerService storeOwnerService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<ApiResponse> singup(@Valid @RequestBody SignUpRequest request) {
        storeOwnerService.signup(request);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(null));
    }
}
