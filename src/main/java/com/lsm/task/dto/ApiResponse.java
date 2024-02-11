package com.lsm.task.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T extends Object> {
    private static final String OK_MESSAGE = "ok";
    private final Meta meta;
    private final T data;

    public ApiResponse(int code, String message, T data) {
        this.meta = new Meta(code, message);
        this.data = data;
    }

    @Getter
    @AllArgsConstructor
    static class Meta {
        private int code;
        private String message;
    }

    public static <T> ApiResponse<?> ofErrorResponse(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public static <T> ApiResponse<?> ofSuccessResponse(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), OK_MESSAGE, data);
    }
}
