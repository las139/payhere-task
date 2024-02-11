package com.lsm.task.service;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.domain.StoreOwner;
import com.lsm.task.dto.SignUpRequest;
import com.lsm.task.repository.StoreOwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private static final String ERROR_MESSAGE_EXISTS_USER = "해당 전화번호로 이미 가입된 사용자가 존재합니다.";

    private final StoreOwnerRepository storeOwnerRepository;

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
}
