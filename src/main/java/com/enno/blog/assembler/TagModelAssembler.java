package com.enno.blog.assembler;

import com.enno.blog.controller.api.TagAPIController;
import com.enno.blog.po.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {
    @Override
    public EntityModel<Tag> toModel(Tag tag) {
        return EntityModel.of(tag, //
                linkTo(methodOn(TagAPIController.class).getOne(tag.getId())).withSelfRel(),
                linkTo(methodOn(TagAPIController.class).getAll()).withRel("tags"));
    }
}
