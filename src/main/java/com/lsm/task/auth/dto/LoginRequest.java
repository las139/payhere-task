package com.lsm.task.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {
    @Schema(description = "휴대전화번호", example = "010-4444-3333")
    @NotNull(message = "휴대전화번호는 필수 값입니다.")
    private final String phoneNumber;

    @Schema(description = "비밀번호", example = "12345")
    @NotNull(message = "비밀번호는 필수 값입니다.")
    private final String password;
}
