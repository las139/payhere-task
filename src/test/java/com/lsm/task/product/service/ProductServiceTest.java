package com.lsm.task.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lsm.task.auth.exception.AuthorizationException;
import com.lsm.task.auth.infrastructure.JwtTokenProvider;
import com.lsm.task.product.domain.Product;
import com.lsm.task.product.domain.Size;
import com.lsm.task.product.dto.RegisterProductRequest;
import com.lsm.task.product.dto.UpdateProductRequest;
import com.lsm.task.product.repository.ProductRepository;
import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.storeowner.repository.StoreOwnerRepository;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private StoreOwnerRepository storeOwnerRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private ProductService productService;

    private StoreOwner storeOwner;
    private Product product;
    private UpdateProductRequest updateProductRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        storeOwner = StoreOwner.builder()
                               .id(1L)
                               .phoneNumber("010-1234-5678")
                               .password("password")
                               .build();

        product = Product.builder()
                         .category("음식")
                         .price(BigDecimal.valueOf(10_000))
                         .cost(BigDecimal.valueOf(5_000))
                         .name("BBQ 후라이드치킨")
                         .description("BBQ 에서 출시한 후라이드 치킨입니다.")
                         .barcode("12394091325190")
                         .expirationDate("2024-07-12")
                         .size(Size.LARGE.name())
                         .storeOwner(storeOwner)
                         .build();
    }

    @Test
    @DisplayName("상품이 정상적으로 등록된다.")
    void register_product_success() {
        // given
        RegisterProductRequest request = new RegisterProductRequest(
            "음식",
            BigDecimal.valueOf(8_000),
            BigDecimal.valueOf(3_000),
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
            BigDecimal.valueOf(8_000),
            BigDecimal.valueOf(3_000),
            "BBQ 후라이드 치킨",
            "BBQ 에서 출시한 후라이드 치킨입니다.",
            "3919482012435",
            LocalDate.now().plusDays(30).toString(),
            Size.LARGE.name()
        );

        // when, then
        assertThrows(IllegalArgumentException.class, () -> productService.register(storeOwner, request), "");
    }

    @Test
    @DisplayName("상품 수정 성공")
    void update_product_success() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        updateProductRequest = new UpdateProductRequest(
            "음료",
            BigDecimal.valueOf(2_000),
            BigDecimal.valueOf(1_000),
            "콜라",
            "시원한 콜라입니다.",
            "987654321",
            LocalDate.now().plusDays(30).toString(),
            Size.LARGE.name()
        );

        // when
        productService.update(storeOwner, 1L, updateProductRequest);

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품을 등록한 사장님이 아닐 경우 수정 실패한다.")
    void update_product_fail_now_owner() {
        // given
        StoreOwner anotherOwner = StoreOwner.builder().id(2L).phoneNumber("010-9999-8888").password("password").build();

        updateProductRequest = new UpdateProductRequest(
            "음료",
            BigDecimal.valueOf(2_000),
            BigDecimal.valueOf(1_000),
            "콜라",
            "시원한 콜라입니다.",
            "987654321",
            LocalDate.now().plusDays(30).toString(),
            Size.LARGE.name()
        );

        // when
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // then
        Exception exception = assertThrows(AuthorizationException.class, () -> {
            productService.update(anotherOwner, 1L, updateProductRequest);
        });

        // then
        assertEquals("상품을 등록한 사장님만 수정 가능합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void delete_product_success() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // when
        productService.delete(storeOwner, 1L);

        // then
        verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("상품을 등록한 사장님이 아닐 경우 삭제 실패한다.")
    void delete_product_fail_now_owner() {
        // given
        StoreOwner anotherOwner = StoreOwner.builder().id(2L).phoneNumber("010-9999-8888").password("password").build();

        // when
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // then
        Exception exception = assertThrows(AuthorizationException.class, () -> {
            productService.delete(anotherOwner, 1L);
        });

        // then
        assertEquals("상품을 등록한 사장님만 삭제 가능합니다.", exception.getMessage());
    }
}
