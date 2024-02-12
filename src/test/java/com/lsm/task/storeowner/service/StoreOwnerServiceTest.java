package com.lsm.task.storeowner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.storeowner.dto.SignUpRequest;
import com.lsm.task.storeowner.repository.StoreOwnerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class StoreOwnerServiceTest {
    public static final String PHONE_NUMBER = "010-4444-3333";
    public static final String PASSWORD = "1234";

    @Mock
    private StoreOwnerRepository storeOwnerRepository;

    @InjectMocks
    private StoreOwnerService storeOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() {
        // given
        SignUpRequest request = new SignUpRequest(PHONE_NUMBER, PASSWORD);
        when(storeOwnerRepository.findByPhoneNumber(request.getPhoneNumber())).thenReturn(Optional.empty());

        // when
        storeOwnerService.signup(request);

        // then
        verify(storeOwnerRepository, times(1)).save(any(StoreOwner.class));
    }

    @Test
    @DisplayName("이미 가입한 회원으로 인한 회원가입 실패")
    void signupFailDueToDuplicatedUser() {
        // given
        SignUpRequest request = new SignUpRequest(PHONE_NUMBER, PASSWORD);
        when(storeOwnerRepository.findByPhoneNumber(request.getPhoneNumber())).thenReturn(Optional.of(new StoreOwner(PHONE_NUMBER, PASSWORD)));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> storeOwnerService.signup(request));
    }

    @Test
    @DisplayName("휴대전화번호가 비어있거나 형식이 맞지 않아 회원가입 실패")
    void signupFailDueToInvalidPhoneNumber() {
        // given
        SignUpRequest emptyRequest = new SignUpRequest("", PASSWORD);
        SignUpRequest invalidRequest = new SignUpRequest("01039384920", PASSWORD);

        // then
        assertThatThrownBy(() -> {
            // when
            storeOwnerService.signup(emptyRequest);
            storeOwnerService.signup(invalidRequest);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
