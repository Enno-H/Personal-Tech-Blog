package com.enno.blog.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.enno.blog.controller.api.UserAPIController;
import com.enno.blog.po.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user, //
                linkTo(methodOn(UserAPIController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(UserAPIController.class).all()).withRel("users"));
    }
}
