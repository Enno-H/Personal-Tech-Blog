package com.enno.blog.service;

import com.enno.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    Type updateType(Long id, Type type);

    Type getTypeByName(String name);

    void deleteType(Long id);



}
