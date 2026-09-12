package com.lionani07.algamoney_api.exception;

public class AlgamoneyResourceNotFoundException extends RuntimeException {

    public AlgamoneyResourceNotFoundException(String msg) {
        super(msg);
    }

    public AlgamoneyResourceNotFoundException(String resource, Long id) {
        super(String.format("%s with id %d not found", resource, id));
    }
}
