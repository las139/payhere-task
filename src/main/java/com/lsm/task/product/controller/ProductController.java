package com.lsm.task.product.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.task.auth.domain.LoginMember;
import com.lsm.task.product.domain.Product;
import com.lsm.task.product.dto.UpdateProductRequest;
import com.lsm.task.product.exception.NoSearchProductException;
import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.common.dto.ApiResponse;
import com.lsm.task.product.dto.RegisterProductRequest;
import com.lsm.task.auth.domain.AuthenticationPrincipal;
import com.lsm.task.product.service.ProductService;
import com.lsm.task.storeowner.service.StoreOwnerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "Product", description = "상품 API Document")
public class ProductController {
    private final ProductService productService;
    private final StoreOwnerService storeOwnerService;

    @Operation(summary = "상품 등록", description = "상품을 개별로 등록한다.")
    @GetMapping("/api/products")
    public ResponseEntity<ApiResponse> getProducts(@AuthenticationPrincipal LoginMember loginMember,
                                                   @RequestParam(value = "cursor", required = false) Long cursor,
                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                   @RequestParam(value = "searchKey") String searchKey) {
        Page<Product> results = productService.getProductsByCursor(loginMember.getId(), cursor, size, searchKey);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(results));
    }

    @Operation(summary = "상품 상세 조회", description = "자신이 등록한 상품의 상세정보를 조회한다.")
    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ApiResponse> getProductDetails(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long productId) {
        StoreOwner storeOwner = storeOwnerService.findStoreOwnerById(loginMember.getId());
        Product product = productService.getProductDetails(storeOwner, productId);
        if (product == null) {
            throw new NoSearchProductException();
        }
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(product));
    }

    @Operation(summary = "상품 리스트 조회", description = "자신이 등록한 상품 리스트를 검색 조회한다.")
    @PostMapping("/api/product")
    public ResponseEntity<ApiResponse> registerProduct(@AuthenticationPrincipal LoginMember loginMember, @Valid @RequestBody RegisterProductRequest request) {
        StoreOwner storeOwner = storeOwnerService.findStoreOwnerById(loginMember.getId());
        productService.register(storeOwner, request);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(null));
    }

    @Operation(summary = "상품 수정", description = "자신이 등록한 상품의 속성을 수정한다.")
    @PatchMapping("/api/products/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long productId, @RequestBody UpdateProductRequest request) {
        StoreOwner storeOwner = storeOwnerService.findStoreOwnerById(loginMember.getId());
        productService.update(storeOwner, productId, request);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(null));
    }

    @Operation(summary = "상품 상세 조회", description = "자신이 등록한 상품을 삭제한다.")
    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long productId) {
        StoreOwner storeOwner = storeOwnerService.findStoreOwnerById(loginMember.getId());
        productService.delete(storeOwner, productId);
        return ResponseEntity.ok().body(ApiResponse.ofSuccessResponse(null));
    }
}
