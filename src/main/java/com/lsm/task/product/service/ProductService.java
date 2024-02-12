package com.lsm.task.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.product.domain.Product;
import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.product.dto.RegisterProductRequest;
import com.lsm.task.auth.infrastructure.JwtTokenProvider;
import com.lsm.task.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void register(StoreOwner storeOwner, RegisterProductRequest request) {
        Product product = Product.builder()
                                 .category(request.getCategory())
                                 .price(request.getPrice())
                                 .cost(request.getCost())
                                 .name(request.getName())
                                 .description(request.getDescription())
                                 .barcode(request.getBarcode())
                                 .expirationDate(request.getExpirationDate())
                                 .size(request.getSize())
                                 .description(request.getDescription())
                                 .storeOwner(storeOwner)
                                 .build();

        productRepository.save(product);
    }
}
