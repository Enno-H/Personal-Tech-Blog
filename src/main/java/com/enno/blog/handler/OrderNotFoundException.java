package com.enno.blog.handler;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Could not find Order " + id);
    }
}