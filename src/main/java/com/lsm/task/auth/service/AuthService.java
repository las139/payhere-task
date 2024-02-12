package com.lsm.task.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.auth.dto.LoginRequest;
import com.lsm.task.auth.dto.TokenResponse;
import com.lsm.task.auth.exception.AuthorizationException;
import com.lsm.task.storeowner.exception.NoSearchStoreOwnerException;
import com.lsm.task.auth.infrastructure.JwtTokenProvider;
import com.lsm.task.auth.domain.LoginMember;
import com.lsm.task.storeowner.repository.StoreOwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private static final String ERROR_MESSAGE_INVALID_TOKEN = "유효하지 않은 토큰입니다.";

    private final StoreOwnerRepository storeOwnerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        StoreOwner storeOwner = storeOwnerRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(NoSearchStoreOwnerException::new);

        // 비밀번호 검증
        storeOwner.checkPassword(request.getPassword());

        String token = jwtTokenProvider.generateToken(storeOwner.getPhoneNumber());
        return new TokenResponse(token);
    }

    @Transactional(readOnly = true)
    public LoginMember findStoreOwnerByToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new AuthorizationException(ERROR_MESSAGE_INVALID_TOKEN);
        }

        String phoneNumber = jwtTokenProvider.getPayload(credentials);
        StoreOwner storeOwner = storeOwnerRepository.findByPhoneNumber(phoneNumber).orElseThrow(NoSearchStoreOwnerException::new);
        return new LoginMember(storeOwner.getId(), storeOwner.getPhoneNumber());
    }
}
