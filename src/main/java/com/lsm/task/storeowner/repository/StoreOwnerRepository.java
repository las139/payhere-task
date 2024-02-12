package com.lsm.task.storeowner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.storeowner.domain.StoreOwner;

public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Long> {
    Optional<StoreOwner> findByPhoneNumber(String phoneNumber);
}
