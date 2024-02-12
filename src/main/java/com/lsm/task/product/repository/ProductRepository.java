package com.lsm.task.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
