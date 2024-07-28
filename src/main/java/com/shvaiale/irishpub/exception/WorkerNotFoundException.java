package com.shvaiale.irishpub.exception;

public class WorkerNotFoundException extends RuntimeException {

    public WorkerNotFoundException() {
    }

    public WorkerNotFoundException(String message) {
        super(message);
    }
}
