package com.lsm.task.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.lsm.task.domain.StoreOwner;

@DataJpaTest
@ActiveProfiles("test")
class StoreOwnerRepositoryTest {
    @Autowired
    StoreOwnerRepository storeOwnerRepository;

    StoreOwner storeOwner;

    @BeforeEach
    void init() {
        //given
        storeOwner = StoreOwner.builder().phoneNumber("010-2222-1111").password("1290391329130").build();
    }

    @Test
    @DisplayName("교사 저장 및 값 비교 테스트")
    void save() {
        // when
        final StoreOwner actual = storeOwnerRepository.save(storeOwner);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getPhoneNumber()).isEqualTo(storeOwner.getPhoneNumber()),
            () -> assertThat(actual.getPassword()).isEqualTo(storeOwner.getPassword())
        );
    }

    @Test
    @DisplayName("교사 저장 후 삭제 테스트")
    void delete() {
        // given
        final StoreOwner actual = storeOwnerRepository.save(storeOwner);

        // when
        storeOwnerRepository.deleteById(actual.getId());
        Optional<StoreOwner> expected = storeOwnerRepository.findById(actual.getId());

        // then
        assertThat(expected).isNotPresent();
    }
}
