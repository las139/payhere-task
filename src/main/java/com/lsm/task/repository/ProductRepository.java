package com.lsm.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
