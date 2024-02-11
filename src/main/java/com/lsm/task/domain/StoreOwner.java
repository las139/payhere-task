package com.lsm.task.domain;

import java.util.List;
import java.util.Objects;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.util.StringUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Audited
@AuditTable("store_owner_log")
@Entity
@Table(name = "store_owner")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreOwner extends BaseEntity {
    private static final String ERR_MSG_PHONE_NUMBER_IS_EMPTY = "휴대전화번호가 비어있습니다.";
    private static final String ERR_MSG_PASSWORD_IS_EMPTY = "비밀번호가 비어있습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @OneToMany(mappedBy = "storeOwner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Product> products;

    @Builder
    public StoreOwner(String phoneNumber, String password) {
        validate(phoneNumber, password);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    private void validate(String phoneNumber, String password) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new IllegalArgumentException(ERR_MSG_PHONE_NUMBER_IS_EMPTY);
        }
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException(ERR_MSG_PASSWORD_IS_EMPTY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreOwner that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
