package com.lsm.task.service;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.domain.StoreOwner;
import com.lsm.task.dto.LoginRequest;
import com.lsm.task.dto.SignUpRequest;
import com.lsm.task.dto.TokenResponse;
import com.lsm.task.exception.NoSearchStoreOwnerException;
import com.lsm.task.infrastructure.JwtTokenProvider;
import com.lsm.task.repository.StoreOwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private static final String ERROR_MESSAGE_EXISTS_USER = "해당 전화번호로 이미 가입된 사용자가 존재합니다.";
    private static final String ERROR_MESSAGE_INVALID_TOKEN = "유효하지 않은 토큰입니다.";

    private final StoreOwnerRepository storeOwnerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(SignUpRequest request) {
        if (isDuplicated(request.getPhoneNumber())) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EXISTS_USER);
        }

        StoreOwner storeOwner = StoreOwner.builder()
                                          .phoneNumber(request.getPhoneNumber())
                                          .password(new BCryptPasswordEncoder().encode(request.getPassword()))  // 비밀번호 암호화
                                          .build();

        storeOwnerRepository.save(storeOwner);
    }

    private boolean isDuplicated(String phoneNumber) {
        StoreOwner storeOwner = storeOwnerRepository.findByPhoneNumber(phoneNumber).orElse(null);
        return !Objects.isNull(storeOwner);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        StoreOwner storeOwner = storeOwnerRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(NoSearchStoreOwnerException::new);

        // 비밀번호 검증
        storeOwner.checkPassword(request.getPassword());

        String token = jwtTokenProvider.generateToken(storeOwner.getPhoneNumber());
        return new TokenResponse(token);
    }
}
