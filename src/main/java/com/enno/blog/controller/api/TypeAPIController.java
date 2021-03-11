package com.enno.blog.controller.api;

import com.enno.blog.assembler.TypeModelAssembler;
import com.enno.blog.po.Type;
import com.enno.blog.service.TypeService;
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
public class TypeAPIController {

    private final TypeService typeService;
    private final TypeModelAssembler assembler;

    TypeAPIController(TypeService typeService, TypeModelAssembler assembler) {
        this.typeService = typeService;
        this.assembler = assembler;
    }

    // Aggregate root
    // type::get-aggregate-root[]
    @GetMapping("/api/types")
    public CollectionModel<EntityModel<Type>> getAll() {

        List<EntityModel<Type>> types = typeService.listType().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(types, linkTo(methodOn(TypeAPIController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/api/types")
    public ResponseEntity<EntityModel<Type>> addType(@RequestBody Type newType) {

        EntityModel<Type> entityModel = assembler.toModel(typeService.saveType(newType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item
    // type::get-single-item[]
    @GetMapping("/api/type/{id}")
    public EntityModel<Type> getOne(@PathVariable Long id) {

        Type type = typeService.getType(id);
        return assembler.toModel(type);
    }
    // end::get-single-item[]

    @PutMapping("/api/types/{id}")
    public ResponseEntity<EntityModel<Type>> updateType(@PathVariable Long id, @RequestBody Type newType) {

        Type updatedType = typeService.updateType(id, newType);
        EntityModel<Type> entityModel = assembler.toModel(updatedType);
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/api/types/{id}")
    public ResponseEntity<?> deleteType(@PathVariable Long id) {

        typeService.deleteType(id);

        return ResponseEntity.noContent().build();
    }

}
