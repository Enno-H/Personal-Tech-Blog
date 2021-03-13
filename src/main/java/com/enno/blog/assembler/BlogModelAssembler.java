package com.enno.blog.assembler;

import com.enno.blog.controller.api.UserAPIController;
import com.enno.blog.po.Blog;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BlogModelAssembler implements RepresentationModelAssembler<Blog, EntityModel<Blog>> {
    @Override
    public EntityModel<Blog> toModel(Blog blog) {
        return EntityModel.of(blog, //
                linkTo(methodOn(UserAPIController.class).one(blog.getId())).withSelfRel(),
                linkTo(methodOn(UserAPIController.class).all()).withRel("blogs"));
    }
}
