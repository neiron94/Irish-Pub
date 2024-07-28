package com.shvaiale.irishpub.exception;

public class AlreadyBookedException extends RuntimeException {

    public AlreadyBookedException() {
    }

    public AlreadyBookedException(String message) {
        super(message);
    }
}
