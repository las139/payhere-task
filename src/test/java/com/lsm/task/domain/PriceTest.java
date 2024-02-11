package com.lsm.task.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {
    @Test
    @DisplayName("가격이 비어있거나 0원 미만일 경우 Exception 이 발생한다.")
    void validate_fail() {
        // then
        assertThatThrownBy(() -> {
            // when
            new Price(null);
            new Price(BigDecimal.valueOf(-1L));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 0원 이상일 경우 정상적으로 생성된다.")
    void validate_success() {
        // when
        Price price = new Price(BigDecimal.valueOf(5));

        // then
        assertThat(price.getValue()).isEqualTo(new BigDecimal(5));
    }

    @Test
    @DisplayName("가격을 더하면 합산 후 반환한다.")
    void add() {
        // given
        Price tv = new Price(BigDecimal.valueOf(3_000));
        Price clothes = new Price(BigDecimal.valueOf(5_000));
        Price expected = new Price(BigDecimal.valueOf(8_000));

        // then
        assertThat(tv.add(clothes)).isEqualTo(expected);
    }

    @Test
    @DisplayName("가격이 더 비싸면 true 를 반환한다.")
    void isMoreExpensive() {
        // given
        Price expencive = new Price(BigDecimal.valueOf(5_500));
        Price cheap = new Price(BigDecimal.valueOf(5_000));

        // when
        boolean result = expencive.isMoreExpensive(cheap);

        // then
        assertThat(result).isTrue();
    }
}
