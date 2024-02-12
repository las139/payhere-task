package com.lsm.task.auth.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lsm.task.auth.dto.LoginRequest;
import com.lsm.task.auth.dto.TokenResponse;
import com.lsm.task.auth.exception.AuthorizationException;
import com.lsm.task.auth.infrastructure.JwtTokenProvider;
import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.storeowner.exception.NoSearchStoreOwnerException;
import com.lsm.task.storeowner.repository.StoreOwnerRepository;

class AuthServiceTest {
    public static final String PHONE_NUMBER = "010-4444-3333";
    public static final String PASSWORD = "1234";

    @Mock
    private StoreOwnerRepository storeOwnerRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    StoreOwner mockStoreOwner;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockStoreOwner = StoreOwner.builder()
                                   .phoneNumber(PHONE_NUMBER)
                                   .password(passwordEncoder.encode(PASSWORD))
                                   .build();
    }

    @Test
    @DisplayName("로그인이 성공한다.")
    void login_success() {
        // given
        when(storeOwnerRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(mockStoreOwner));
        when(jwtTokenProvider.generateToken(anyString())).thenReturn("token");

        // when
        LoginRequest loginRequest = new LoginRequest(PHONE_NUMBER, PASSWORD);
        TokenResponse tokenResponse = authService.login(loginRequest);

        // then
        assertEquals("token", tokenResponse.getAccessToken());
    }

    @Test
    @DisplayName("존재하지 않는 유저의 휴대전화번호를 입력하면 로그인 실패한다.")
    void login_fail_store_owner_not_found() {
        when(storeOwnerRepository.findByPhoneNumber(PHONE_NUMBER)).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest(PHONE_NUMBER, PASSWORD);

        assertThrows(NoSearchStoreOwnerException.class, () -> authService.login(request));
    }

    @Test
    @DisplayName("잘못된 비밀번호를 입력하면 로그인 실패한다.")
    void login_fail_incorrect_password() {
        // given
        when(storeOwnerRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(mockStoreOwner));
        LoginRequest request = new LoginRequest(PHONE_NUMBER, PASSWORD + "wkjid");

        // then
        assertThrows(AuthorizationException.class, () -> {
            // when
            authService.login(request);
        }, "");
    }
}
