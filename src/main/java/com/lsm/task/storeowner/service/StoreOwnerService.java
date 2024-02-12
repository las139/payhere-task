package com.lsm.task.storeowner.service;

import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.auth.infrastructure.JwtTokenProvider;
import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.storeowner.dto.SignUpRequest;
import com.lsm.task.storeowner.exception.NoSearchStoreOwnerException;
import com.lsm.task.storeowner.repository.StoreOwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreOwnerService {
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

    public StoreOwner findStoreOwnerById(Long storeOwnerId) {
        return storeOwnerRepository.findById(storeOwnerId).orElseThrow(NoSearchStoreOwnerException::new);
    }
}
