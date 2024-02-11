package com.lsm.task.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CostTest {
    @Test
    @DisplayName("가격이 비어있거나 0원 미만일 경우 Exception 이 발생한다.")
    void validate() {
        // then
        assertThatThrownBy(() -> {
            // when
            new Cost(null);
            new Cost(BigDecimal.valueOf(-1L));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("원가가 0원 이상일 경우 정상적으로 생성된다.")
    void validate_success() {
        // when
        Cost cost = new Cost(BigDecimal.valueOf(5));

        // then
        assertThat(cost.getValue()).isEqualTo(new BigDecimal(5));
    }

    @Test
    @DisplayName("원가를 더하면 합산 후 반환한다.")
    void add() {
        // given
        Cost tv = new Cost(BigDecimal.valueOf(3_000));
        Cost clothes = new Cost(BigDecimal.valueOf(5_000));
        Cost expected = new Cost(BigDecimal.valueOf(8_000));

        // then
        assertThat(tv.add(clothes)).isEqualTo(expected);
    }

    @Test
    @DisplayName("원가가 더 비싸면 true 를 반환한다.")
    void isMoreExpensive() {
        // given
        Cost expencive = new Cost(BigDecimal.valueOf(5_500));
        Cost cheap = new Cost(BigDecimal.valueOf(5_000));

        // when
        boolean result = expencive.isMoreExpensive(cheap);

        // then
        assertThat(result).isTrue();
    }
}
