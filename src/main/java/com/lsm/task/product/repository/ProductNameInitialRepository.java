package com.lsm.task.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.product.domain.ProductNameInitial;

public interface ProductNameInitialRepository extends JpaRepository<ProductNameInitial, Long> {
}
