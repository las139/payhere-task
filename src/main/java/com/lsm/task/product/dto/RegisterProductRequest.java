package com.lsm.task.product.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterProductRequest {
    @Schema(description = "category", example = "음식")
    @NotNull(message = "카테고리는 필수 값입니다.")
    private final String category;

    @Schema(description = "가격", example = "15000")
    @NotNull(message = "가격은 필수 값입니다.")
    private final BigDecimal price;

    @Schema(description = "원가", example = "3000")
    @NotNull(message = "원가는 필수 값입니다.")
    private final BigDecimal cost;

    @Schema(description = "상품명", example = "BBQ 후라이드 치킨")
    @NotNull(message = "상품명은 필수 값입니다.")
    private final String name;

    @Schema(description = "설명", example = "음식에 대한 설명입니다.")
    private final String description;

    @Schema(description = "바코드", example = "124309215478129015")
    @NotNull(message = "바코드는 필수 값입니다.")
    private final String barcode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "유통기한", example = "2024-02-07")
    @NotNull(message = "유통기한은 필수 값입니다.")
    private final String expirationDate;

    @Schema(description = "사이즈", example = "SMALL")
    @NotNull(message = "사이즈는 필수 값입니다.")
    private final String size;
}
