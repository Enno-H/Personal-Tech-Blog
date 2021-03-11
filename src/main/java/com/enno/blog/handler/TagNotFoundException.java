package com.enno.blog.handler;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(Long id) {
        super("Could not find Tag " + id);
    }
}