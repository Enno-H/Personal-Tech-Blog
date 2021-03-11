package com.enno.blog.controller.api;

import com.enno.blog.assembler.TagModelAssembler;
import com.enno.blog.po.Tag;
import com.enno.blog.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TagAPIController {

    private final TagService tagService;
    private final TagModelAssembler assembler;

    TagAPIController(TagService tagService, TagModelAssembler assembler) {
        this.tagService = tagService;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/tags")
    public CollectionModel<EntityModel<Tag>> getAll() {

        List<EntityModel<Tag>> tags = tagService.listTag().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(tags, linkTo(methodOn(TagAPIController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/api/tags")
    public ResponseEntity<EntityModel<Tag>> addTag(@RequestBody Tag newTag) {

        EntityModel<Tag> entityModel = assembler.toModel(tagService.saveTag(newTag));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item
    // tag::get-single-item[]
    @GetMapping("/api/tag/{id}")
    public EntityModel<Tag> getOne(@PathVariable Long id) {

        Tag tag = tagService.getTag(id);
        return assembler.toModel(tag);
    }
    // end::get-single-item[]

    @PutMapping("/api/tags/{id}")
    public ResponseEntity<EntityModel<Tag>> updateTag(@PathVariable Long id, @RequestBody Tag newTag) {

        Tag updatedTag = tagService.updateTag(id, newTag);
        EntityModel<Tag> entityModel = assembler.toModel(updatedTag);
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/api/tags/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {

        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }

}
