package com.enno.blog.controller.api;
import com.enno.blog.assembler.UserModelAssembler;
import com.enno.blog.dao.UserRepository;
import com.enno.blog.handler.UserNotFoundException;
import com.enno.blog.po.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserAPIController {

    private final UserRepository repository;
    private final UserModelAssembler assembler;

    UserAPIController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {

//        List<EntityModel<User>> users = repository.findAll().stream()
//                .map(User -> EntityModel.of(User,
//                        linkTo(methodOn(UserAPIController.class).one(User.getId())).withSelfRel(),
//                        linkTo(methodOn(UserAPIController.class).all()).withRel("users")))
//                .collect(Collectors.toList());
        List<EntityModel<User>> users = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserAPIController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    public User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item
    // tag::get-single-item[]
    @GetMapping("/user/{id}")
    public EntityModel<User> one(@PathVariable Long id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

//        return EntityModel.of(user, //
//                linkTo(methodOn(UserAPIController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(UserAPIController.class).all()).withRel("users"));
        return assembler.toModel(user);
    }
    // end::get-single-item[]

    @PutMapping("/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(User -> {
                    User.setUsername(newUser.getUsername());
                    User.setEmail(newUser.getEmail());
                    User.setType(newUser.getType());
                    User.setAvatar(newUser.getAvatar());
                    User.setNickname(newUser.getNickname());
                    return repository.save(User);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {

        repository.deleteById(id);
    }

}
