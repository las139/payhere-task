package com.lsm.task.product.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lsm.task.common.domain.BaseEntity;
import com.lsm.task.product.dto.UpdateProductRequest;
import com.lsm.task.storeowner.domain.StoreOwner;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Audited
@AuditTable("product_log")
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product extends BaseEntity {
    private static final String ERROR_MESSAGE_CATEGORY_IS_EMPTY = "카테고리가 비어있습니다.";
    private static final String ERROR_MESSAGE_NAME_IS_EMPTY = "상품명이 비어있습니다.";
    private static final String ERROR_MESSAGE_BARCODE_IS_EMPTY = "바코드가 비어있습니다.";
    private static final String ERROR_MESSAGE_EXPIRATION_DATE_IS_EMPTY = "유통기한이 비어있습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Embedded
    private Price price;

    @Embedded
    private Cost cost;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "barcode", length = 255, nullable = false, unique = true)
    private String barcode;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", length = 10, nullable = false)
    private Size size;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "fk_product_to_user"))
    private StoreOwner storeOwner;

    @Builder
    public Product(Long id, String category, BigDecimal price, BigDecimal cost, String name, String description, String barcode,
                   String expirationDate, String size, StoreOwner storeOwner) {
        validate(category, name, barcode, expirationDate);
        this.category = category;
        this.price = new Price(price);
        this.cost = new Cost(cost);
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = LocalDate.parse(expirationDate, DateTimeFormatter.ISO_LOCAL_DATE);
        this.size = Size.getSizeByStr(size);
        this.storeOwner = storeOwner;
    }

    private void validate(String category, String name, String barcode, String expirationDate) {
        if (!StringUtils.hasText(category)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_CATEGORY_IS_EMPTY);
        }
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NAME_IS_EMPTY);
        }
        if (!StringUtils.hasText(barcode)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_BARCODE_IS_EMPTY);
        }
        if (!StringUtils.hasText(expirationDate)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_EXPIRATION_DATE_IS_EMPTY);
        }
    }

    public boolean isOwner(StoreOwner storeOwner) {
        return this.storeOwner.equals(storeOwner);
    }

    public void update(UpdateProductRequest request) {
        if (request.getCategory() != null) {
            this.category = request.getCategory();
        }
        if (request.getPrice() != null) {
            this.price = new Price(request.getPrice());
        }
        if (request.getCost() != null) {
            this.cost = new Cost(request.getCost());
        }
        if (request.getName() != null) {
            this.name = request.getName();
        }
        if (request.getDescription() != null) {
            this.description = request.getDescription();
        }
        if (request.getBarcode() != null) {
            this.barcode = request.getBarcode();
        }
        if (request.getExpirationDate() != null) {
            this.expirationDate = LocalDate.parse(request.getExpirationDate());
        }
        if (request.getSize() != null) {
            this.size = Size.getSizeByStr(request.getSize());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
