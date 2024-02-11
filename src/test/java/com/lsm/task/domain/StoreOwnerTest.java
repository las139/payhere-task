package com.lsm.task.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreOwnerTest {
    @Test
    @DisplayName("휴대전화번호가 비어있는 경우 유저 생성 시 Exception 이 발생한다.")
    void validate_phonNumber_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            StoreOwner.builder().phoneNumber(null).password("asdkjqwp1290-u091").build();
            // when
            StoreOwner.builder().phoneNumber("").password("asdkjqwp1290-u091").build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 비어있는 경우 유저 생성 시 Exception 이 발생한다.")
    void validate_password_empty() {
        // then
        assertThatThrownBy(() -> {
            // when
            StoreOwner.builder().phoneNumber("01044447777").password(null).build();
            StoreOwner.builder().phoneNumber("01044447777").password("").build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("휴대전화번호, 비밀번호가 정상적인 데이터일 경우 유저가 정상적으로 생성된다.")
    void validate_success() {
        // when
        StoreOwner storeOwner = new StoreOwner("01044447777", "asdkjqwp1290-u091");

        assertAll(
            () -> assertThat(storeOwner.getPhoneNumber()).isEqualTo("01044447777"),
            () -> assertThat(storeOwner.getPassword()).isEqualTo("asdkjqwp1290-u091")
        );
    }
}
