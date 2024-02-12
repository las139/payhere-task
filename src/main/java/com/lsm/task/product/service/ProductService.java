package com.lsm.task.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.auth.exception.AuthorizationException;
import com.lsm.task.product.domain.Product;
import com.lsm.task.product.domain.ProductNameInitial;
import com.lsm.task.product.dto.UpdateProductRequest;
import com.lsm.task.product.exception.NoSearchProductException;
import com.lsm.task.product.repository.ProductNameInitialRepository;
import com.lsm.task.product.utils.ExtractInitialUtils;
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

    private final ProductNameInitialRepository nameInitialRepository;

    public Page<Product> getProductsByCursor(Long ownerId, Long cursorId, int pageSize, String searchKey) {
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        long cursor = (cursorId == null ? Long.MAX_VALUE : cursorId);
        String searchInitial = ExtractInitialUtils.extractWord(searchKey);
        return productRepository.findByStoreOwnerAndCursor(ownerId, searchKey, searchInitial, cursor, pageable);
    }

    public Product getProductDetails(StoreOwner storeOwner, Long productId) {
        return productRepository.findByStoreOwnerAndId(storeOwner, productId);
    }

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

        // 상품명 초성 단어로 분리하여 저장
        List<Map<String, String>> initials = ExtractInitialUtils.extractInitial(request.getName());
        for (Map<String, String> map : initials) {
            ProductNameInitial productNameInitial = ProductNameInitial.builder()
                                                                      .seq(initials.indexOf(map) + 1)
                                                                      .word(map.get("word"))
                                                                      .initial(map.get("initial"))
                                                                      .product(product)
                                                                      .build();

            nameInitialRepository.save(productNameInitial);
        }
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
