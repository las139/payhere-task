package com.lsm.task.product.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterProductRequest {
    @NotNull(message = "카테고리는 필수 값입니다.")
    private final String category;

    @NotNull(message = "가격은 필수 값입니다.")
    private final BigDecimal price;

    @NotNull(message = "원가는 필수 값입니다.")
    private final BigDecimal cost;

    @NotNull(message = "상품명은 필수 값입니다.")
    private final String name;

    private final String description;

    @NotNull(message = "바코드는 필수 값입니다.")
    private final String barcode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "유통기한은 필수 값입니다.")
    private final String expirationDate;

    @NotNull(message = "비밀번호는 필수 값입니다.")
    private final String size;
}
