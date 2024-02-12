package com.lsm.task.storeowner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {
    @Schema(description = "휴대전화번호", example = "010-4444-7777")
    @NotNull(message = "휴대전화번호는 필수 값입니다.")
    private final String phoneNumber;

    @Schema(description = "비밀번호", example = "1234")
    @NotNull(message = "비밀번호는 필수 값입니다.")
    private final String password;
}
