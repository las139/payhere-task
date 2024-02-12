package com.lsm.task.product.domain;

import java.util.Objects;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lsm.task.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@AuditTable("product_name_initial_log")
@Entity
@Table(name = "product_name_initial")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductNameInitial extends BaseEntity {
    private static final String ERROR_MESSAGE_WORD_IS_EMPTY = "단어가 비어있습니다.";
    private static final String ERROR_MESSAGE_INITIAL_IS_EMPTY = "초성이 비어있습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seq", nullable = false)
    private int seq;

    @Column(name = "word", length = 50, nullable = false)
    private String word;

    @Column(name = "initial", length = 50, nullable = false)
    private String initial;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_name_initial_to_product"))
    private Product product;

    @Builder
    public ProductNameInitial(Long id, int seq, String word, String initial, Product product) {
        validate(word, initial);
        this.id = id;
        this.seq = seq;
        this.word = word;
        this.initial = initial;
        this.product = product;
    }

    private void validate(String word, String initial) {
        if (!StringUtils.hasText(word)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_WORD_IS_EMPTY);
        }
        if (!StringUtils.hasText(initial)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INITIAL_IS_EMPTY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductNameInitial that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
