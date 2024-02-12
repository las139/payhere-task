package com.lsm.task.product.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateProductRequest {
    private final String category;

    private final BigDecimal price;

    private final BigDecimal cost;

    private final String name;

    private final String description;

    private final String barcode;

    private final String expirationDate;

    private final String size;
}
