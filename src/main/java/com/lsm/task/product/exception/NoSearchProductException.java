package com.lsm.task.product.exception;

import java.util.NoSuchElementException;

public class NoSearchProductException extends NoSuchElementException {
    private static final String MESSAGE = "해당하는 상품을 찾을 수 없습니다.";

    public NoSearchProductException() {
        super(String.format(MESSAGE));
    }
}
