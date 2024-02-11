package com.lsm.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.StoreOwner;

public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Long> {
    Optional<StoreOwner> findByPhoneNumber(String phoneNumber);
}
