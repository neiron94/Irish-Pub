package com.shvaiale.irishpub.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Integer id) {
        super("Customer with id %d not found".formatted(id));
    }
}
