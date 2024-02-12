package com.lsm.task.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {
    @Schema(description = "액세스토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMTAtNDQ0NC0zMzMzIiwiaWF0IjoxNzA3NzQ2Nzc0LCJleHAiOjE3MDc3NTAzNzR9.pplgz8pIeHNn5-mjpBZJBCTf93eqxGuCm3qxgeg2x50")
    private String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
