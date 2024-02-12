package com.lsm.task.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lsm.task.product.domain.Product;
import com.lsm.task.storeowner.domain.StoreOwner;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p WHERE p.storeOwner.id = :ownerId AND p.id < :cursor ORDER BY p.id DESC")
    Page<Product> findByStoreOwnerAndCursor(@Param("ownerId") Long ownerId, @Param("cursor") Long cursor, Pageable pageable);

    Product findByStoreOwnerAndId(StoreOwner storeOwner, Long id);
}
