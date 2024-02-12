package com.lsm.task.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.lsm.task.storeowner.domain.StoreOwner;

class ProductTest {
    @Test
    @DisplayName("카테고리가 비어있는 경우 상품 생성 시 Exception 이 발생한다.")
    void validate_category_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            Product.builder().category(null).build();
            // when
            Product.builder().category("").build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명이 비어있는 경우 상품 생성 시 Exception 이 발생한다.")
    void validate_name_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            Product.builder().name(null).build();
            // when
            Product.builder().name("").build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("설명이 비어있는 경우 상품 생성 시 Exception 이 발생한다.")
    void validate_description_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            Product.builder().description(null).build();
            // when
            Product.builder().description("").build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유통기한이 비어있는 경우 상품 생성 시 Exception 이 발생한다.")
    void validate_expirationDate_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            Product.builder().expirationDate(null).build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 비어있거나 0원 미만일 경우 상품 생성 시 Exception 이 발생한다.")
    void validate_price_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            Product.builder().price(null).build();
            Product.builder().price(BigDecimal.valueOf(-1L)).build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("원가가 비어있거나 0원 미만일 경우 상품 생성 시 Exception 이 발생한다.")
    void validate_cost_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            Product.builder().cost(null).build();
            Product.builder().cost(BigDecimal.valueOf(-1L)).build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("정상적인 정보 입력 시 상품이 생성된다.")
    void validate_success() {
        // given
        StoreOwner storeOwner = new StoreOwner("010-4444-7777", "asdkjqwp1290-u091");

        // when
        Product product = Product.builder()
                                 .category("음식")
                                 .price(BigDecimal.valueOf(8_000))
                                 .cost(BigDecimal.valueOf(5_000))
                                 .name("BBQ 후라이드치킨")
                                 .description("BBQ 에서 출시한 후라이드 치킨입니다.")
                                 .barcode("12394091325190")
                                 .expirationDate("2024-07-12")
                                 .size(Size.LARGE.name())
                                 .storeOwner(storeOwner)
                                 .build();

        assertAll(
            () -> assertThat(product.getCategory()).isEqualTo("음식"),
            () -> assertThat(product.getPrice().getValue()).isEqualTo(BigDecimal.valueOf(8_000)),
            () -> assertThat(product.getCost().getValue()).isEqualTo(BigDecimal.valueOf(5_000)),
            () -> assertThat(product.getName()).isEqualTo("BBQ 후라이드치킨"),
            () -> assertThat(product.getDescription()).isEqualTo("BBQ 에서 출시한 후라이드 치킨입니다."),
            () -> assertThat(product.getExpirationDate()).isNotNull(),
            () -> assertThat(product.getSize()).isEqualTo(Size.LARGE),
            () -> assertThat(product.getStoreOwner()).isEqualTo(storeOwner)
        );
    }
}
