package com.lsm.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {
    @NotNull(message = "휴대전화번호는 필수 값입니다.")
    private final String phoneNumber;

    @NotNull(message = "비밀번호는 필수 값입니다.")
    private final String password;
}
