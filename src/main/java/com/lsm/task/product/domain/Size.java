package com.lsm.task.product.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Size {
    SMALL(0, "소"),
    LARGE(1, "대");

    private final int value;
    private final String description;

    public static Size getSizeByStr(String size) {
        return Arrays.stream(values())
                     .filter(v -> v.name().equals(size))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사이즈 입니다"));
    }
}
