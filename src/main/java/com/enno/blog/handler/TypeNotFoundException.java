package com.enno.blog.handler;

public class TypeNotFoundException extends RuntimeException {

    public TypeNotFoundException(Long id) {
        super("Could not find Type " + id);
    }
}