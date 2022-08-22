package com.icom.cart.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartNotFoundException extends Exception {
    public CartNotFoundException(String errorMessage) {
        super(errorMessage);
        log.debug(errorMessage);
    }
}
