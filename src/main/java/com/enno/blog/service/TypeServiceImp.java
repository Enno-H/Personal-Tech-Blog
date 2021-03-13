package com.enno.blog.service;

import com.enno.blog.NotFoundException;
import com.enno.blog.dao.TypeRepository;
import com.enno.blog.handler.TypeNotFoundException;
import com.enno.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImp implements TypeService{

    private final TypeRepository typeRepository;

    public TypeServiceImp(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(id));
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("blogs.size").descending());
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("The type does not exist.");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }

    @Override
    public Boolean existsTypeByName(String name) {
        return typeRepository.existsTypeByName(name);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
