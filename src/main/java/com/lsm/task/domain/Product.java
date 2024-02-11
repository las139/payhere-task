package com.lsm.task.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.util.StringUtils;

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
public class Product extends BaseEntity {
    private static final String ERR_MSG_CATEGORY_IS_EMPTY = "카테고리가 비어있습니다.";
    private static final String ERR_MSG_NAME_IS_EMPTY = "상품명이 비어있습니다.";
    private static final String ERR_MSG_DESCRIPTION_IS_EMPTY = "설명이 비어있습니다.";
    private static final String ERR_MSG_EXPIRATION_DATE_IS_EMPTY = "유통기한이 비어있습니다.";

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

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", length = 10, nullable = false)
    private Size size;

    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "fk_product_to_user"))
    private StoreOwner storeOwner;

    @Builder
    public Product(String category, BigDecimal price, BigDecimal cost, String name, String description, LocalDateTime expirationDate,
                   String size, StoreOwner storeOwner) {
        validate(category, name, description, expirationDate);
        this.category = category;
        this.price = new Price(price);
        this.cost = new Cost(cost);
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
        this.size = Size.getSizeByStr(size);
        this.storeOwner = storeOwner;
    }

    private void validate(String category, String name, String description, LocalDateTime expirationDate) {
        if (!StringUtils.hasText(category)) {
            throw new IllegalArgumentException(ERR_MSG_CATEGORY_IS_EMPTY);
        }
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException(ERR_MSG_NAME_IS_EMPTY);
        }
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException(ERR_MSG_DESCRIPTION_IS_EMPTY);
        }
        if (Objects.isNull(expirationDate)) {
            throw new IllegalArgumentException(ERR_MSG_EXPIRATION_DATE_IS_EMPTY);
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
