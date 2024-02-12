package com.lsm.task.product.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateProductRequest {
    @Schema(description = "category", example = "음식")
    private final String category;

    @Schema(description = "가격", example = "15000")
    private final BigDecimal price;

    @Schema(description = "원가", example = "3000")
    private final BigDecimal cost;

    @Schema(description = "상품명", example = "BBQ 후라이드 치킨")
    private final String name;

    @Schema(description = "설명", example = "음식에 대한 설명입니다.")
    private final String description;

    @Schema(description = "바코드", example = "124309215478129015")
    private final String barcode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "유통기한", example = "2024-02-07")
    private final String expirationDate;

    @Schema(description = "사이즈", example = "SMALL")
    private final String size;
}
