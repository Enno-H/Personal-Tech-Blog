package com.enno.blog.assembler;

import com.enno.blog.controller.api.TypeAPIController;
import com.enno.blog.po.Type;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TypeModelAssembler implements RepresentationModelAssembler<Type, EntityModel<Type>> {
    @Override
    public EntityModel<Type> toModel(Type type) {
        return EntityModel.of(type,
                linkTo(methodOn(TypeAPIController.class).getOne(type.getId())).withSelfRel(),
                linkTo(methodOn(TypeAPIController.class).getAll()).withRel("types"));
    }
}
