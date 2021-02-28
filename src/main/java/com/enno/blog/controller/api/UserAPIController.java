package com.enno.blog.controller.api;
import com.enno.blog.dao.UserRepository;
import com.enno.blog.handler.UserNotFoundException;
import com.enno.blog.po.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAPIController {

    private final UserRepository repository;

    UserAPIController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/Users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/Users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

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

    @DeleteMapping("/Users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
