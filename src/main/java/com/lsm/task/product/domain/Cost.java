package com.lsm.task.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cost {
    private static final String ERROR_MESSAGE_INVALID_PRICE = "원가는 0 원 이상이어야 합니다.";

    @Column(name = "cost", nullable = false)
    private BigDecimal value;

    public Cost(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_PRICE);
        }
    }

    public Cost add(Cost cost) {
        return new Cost(this.value.add(cost.getValue()));
    }

    public boolean isMoreExpensive(Cost cost) {
        return this.value.compareTo(cost.getValue()) > 0;
    }
}
