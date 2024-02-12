package com.lsm.task.product.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.lsm.task.product.domain.Product;
import com.lsm.task.product.domain.Size;
import com.lsm.task.storeowner.domain.StoreOwner;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void init() {
        //given
        StoreOwner storeOwner = StoreOwner.builder().phoneNumber("010-2222-1111").password("1290391329130").build();
        product = Product.builder()
                         .category("음식")
                         .price(BigDecimal.valueOf(8_000))
                         .cost(BigDecimal.valueOf(5_000))
                         .name("BBQ 후라이드치킨")
                         .description("BBQ 에서 출시한 후라이드 치킨입니다.")
                         .expirationDate("2024-07-12")
                         .size(Size.LARGE.name())
                         .storeOwner(storeOwner)
                         .build();
    }

    @Test
    @DisplayName("교사 저장 및 값 비교 테스트")
    void save() {
        // when
        final Product actual = productRepository.save(product);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getCategory()).isEqualTo(product.getCategory()),
            () -> assertThat(actual.getPrice()).isEqualTo(product.getPrice()),
            () -> assertThat(actual.getCost()).isEqualTo(product.getCost()),
            () -> assertThat(actual.getName()).isEqualTo(product.getName()),
            () -> assertThat(actual.getDescription()).isEqualTo(product.getDescription()),
            () -> assertThat(actual.getExpirationDate()).isEqualTo(product.getExpirationDate()),
            () -> assertThat(actual.getSize()).isEqualTo(product.getSize()),
            () -> assertThat(actual.getStoreOwner()).isEqualTo(product.getStoreOwner())
        );
    }

    @Test
    @DisplayName("교사 저장 후 삭제 테스트")
    void delete() {
        // given
        final Product actual = productRepository.save(product);

        // when
        productRepository.deleteById(actual.getId());
        Optional<Product> expected = productRepository.findById(actual.getId());

        // then
        assertThat(expected).isNotPresent();
    }
}
