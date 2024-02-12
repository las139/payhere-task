package com.lsm.task.product.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lsm.task.auth.infrastructure.JwtTokenProvider;
import com.lsm.task.product.domain.Product;
import com.lsm.task.product.domain.Size;
import com.lsm.task.product.dto.RegisterProductRequest;
import com.lsm.task.product.repository.ProductRepository;
import com.lsm.task.storeowner.domain.StoreOwner;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private ProductService productService;

    StoreOwner storeOwner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        storeOwner = StoreOwner.builder()
                               .phoneNumber("010-1234-5678")
                               .password("password")
                               .build();
    }

    @Test
    @DisplayName("상품이 정상적으로 등록된다.")
    void register_product_success() {
        // given
        RegisterProductRequest request = new RegisterProductRequest(
            "음식",
            new BigDecimal("8000"),
            new BigDecimal("3000"),
            "BBQ 후라이드 치킨",
            "BBQ 에서 출시한 후라이드 치킨입니다.",
            "3919482012435",
            "2024-07-05",
            Size.LARGE.name()
        );

        // when
        productService.register(storeOwner, request);

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 정보가 비정상적일 경우 등록에 실패한다.")
    void registerFailsWhenCategoryIsMissing() {
        // given
        RegisterProductRequest request = new RegisterProductRequest(
            null,
            new BigDecimal("8000"),
            new BigDecimal("3000"),
            "BBQ 후라이드 치킨",
            "BBQ 에서 출시한 후라이드 치킨입니다.",
            "3919482012435",
            LocalDate.now().plusDays(30).toString(),
            Size.LARGE.name()
        );

        // when, then
        assertThrows(IllegalArgumentException.class, () -> productService.register(storeOwner, request), "");
    }
}
