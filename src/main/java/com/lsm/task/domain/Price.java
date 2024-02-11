package com.lsm.task.domain;

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
public class Price {
    private static final String ERROR_MESSAGE_INVALID_PRICE = "가격은 0 원 이상이어야 합니다.";

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    public Price(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_PRICE);
        }
    }

    public Price add(Price price) {
        return new Price(this.value.add(price.value));
    }

    public boolean isMoreExpensive(Price price) {
        return this.value.compareTo(price.getValue()) > 0;
    }
}
