package com.lsm.task.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.auth.domain.LoginMember;
import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.common.dto.ApiResponse;
import com.lsm.task.product.dto.RegisterProductRequest;
import com.lsm.task.auth.domain.AuthenticationPrincipal;
import com.lsm.task.product.service.ProductService;
import com.lsm.task.storeowner.service.StoreOwnerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;
    private final StoreOwnerService storeOwnerService;

    @PostMapping("/api/product")
    public ResponseEntity<ApiResponse> registerProduct(@AuthenticationPrincipal LoginMember loginMember, @Valid @RequestBody RegisterProductRequest request) {
        StoreOwner storeOwner = storeOwnerService.findStoreOwnerById(loginMember.getId());
        productService.register(storeOwner, request);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(null));
    }
}
