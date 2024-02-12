package com.lsm.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginMember {
    private Long id;
    private String phoneNumber;

    public boolean isLogin() {
        return this.id != null;
    }
}
