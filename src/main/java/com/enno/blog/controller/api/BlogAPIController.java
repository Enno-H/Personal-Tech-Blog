package com.enno.blog.controller.api;

import com.enno.blog.assembler.BlogModelAssembler;
import com.enno.blog.po.Blog;
import com.enno.blog.po.Tag;
import com.enno.blog.po.Type;
import com.enno.blog.service.BlogService;
import com.enno.blog.service.TagService;
import com.enno.blog.service.TypeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BlogAPIController {

    private final BlogService blogService;
    private final TypeService typeService;
    private final TagService tagService;
    private final BlogModelAssembler assembler;

    BlogAPIController(BlogService blogService, TypeService typeService, TagService tagService, BlogModelAssembler assembler) {
        this.blogService = blogService;
        this.typeService = typeService;
        this.tagService = tagService;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("api/blogs")
    public CollectionModel<EntityModel<Blog>> all(@PageableDefault(size = 8, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        List<EntityModel<Blog>> blogs = new ArrayList<>();
        for (Blog blog : blogService.listBlog(pageable)) {
            EntityModel<Blog> blogEntityModel = assembler.toModel(blog);
            blogs.add(blogEntityModel);
        }

        return CollectionModel.of(blogs, linkTo(methodOn(BlogAPIController.class).all(pageable)).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("api/blogs")
    public ResponseEntity<EntityModel<Blog>> newBlog(@RequestBody Blog newBlog) {

        String inputTypeName = newBlog.getType().getName();
        newBlog.setType(typeService.getOrElseCreateType(inputTypeName));

        List<String> tagNameList = newBlog.getTags().stream()
                .map(tag -> tag.getName())
                .collect(Collectors.toList());
        newBlog.setTags(tagService.getOrElseCreateTagList(tagNameList));


        EntityModel<Blog> entityModel = assembler.toModel(blogService.saveBlog(newBlog));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item
    // tag::get-single-item[]
    @GetMapping("api/blog/{id}")
    public EntityModel<Blog> one(@PathVariable Long id) {

        Blog blog = blogService.getBlog(id);
        return assembler.toModel(blog);
    }
    // end::get-single-item[]

//    @PutMapping("/users/{id}")
//    public ResponseEntity<EntityModel<User>> replaceUser(@RequestBody User newUser, @PathVariable Long id) {
//
//        User updatedUser = repository.findById(id)
//                .map(user -> {
//                    user.setUsername(newUser.getUsername());
//                    user.setEmail(newUser.getEmail());
//                    user.setType(newUser.getType());
//                    user.setAvatar(newUser.getAvatar());
//                    user.setNickname(newUser.getNickname());
//                    return repository.save(user);
//                })
//                .orElseGet(() -> {
//                    newUser.setId(id);
//                    return repository.save(newUser);
//                });
//
//        EntityModel<User> entityModel = assembler.toModel(updatedUser);
//        return ResponseEntity //
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
//                .body(entityModel);
//    }

    @DeleteMapping("api/blog/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        blogService.deleteBlog(id);

        return ResponseEntity.noContent().build();
    }

}
