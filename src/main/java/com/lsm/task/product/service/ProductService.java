package com.lsm.task.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.auth.exception.AuthorizationException;
import com.lsm.task.product.domain.Product;
import com.lsm.task.product.dto.UpdateProductRequest;
import com.lsm.task.product.exception.NoSearchProductException;
import com.lsm.task.storeowner.domain.StoreOwner;
import com.lsm.task.product.dto.RegisterProductRequest;
import com.lsm.task.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private static final String ERROR_MESSAGE_UPDATE_NOT_OWNER = "상품을 등록한 사장님만 수정 가능합니다.";
    private static final String ERROR_MESSAGE_DELETE_NOT_OWNER = "상품을 등록한 사장님만 삭제 가능합니다.";

    private final ProductRepository productRepository;

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

    public void update(StoreOwner storeOwner, Long productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(NoSearchProductException::new);

        // 상품을 등록한 사장님만 수정 가능
        if (!product.isOwner(storeOwner)) {
            throw new AuthorizationException(ERROR_MESSAGE_UPDATE_NOT_OWNER);
        }

        product.update(request);

        productRepository.save(product);
    }

    public void delete(StoreOwner storeOwner, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(NoSearchProductException::new);

        if (!product.isOwner(storeOwner)) {
            throw new AuthorizationException(ERROR_MESSAGE_DELETE_NOT_OWNER);
        }

        productRepository.delete(product);
    }
}
