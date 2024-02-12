package com.lsm.task.storeowner.exception;

import java.util.NoSuchElementException;

public class NoSearchStoreOwnerException extends NoSuchElementException {
    private static final String MESSAGE = "해당하는 사용자를 찾을 수 없습니다.";

    public NoSearchStoreOwnerException() {
        super(String.format(MESSAGE));
    }
}
