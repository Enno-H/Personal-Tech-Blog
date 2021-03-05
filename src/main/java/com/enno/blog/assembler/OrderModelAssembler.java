package com.enno.blog.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.enno.blog.controller.api.OrderAPIController;
import com.enno.blog.po.Order;
import com.enno.blog.po.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public
class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderAPIController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderAPIController.class).all()).withRel("orders"));

        // Conditional links based on state of the order

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderAPIController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderAPIController.class).complete(order.getId())).withRel("complete"));
        }

        return orderModel;
    }
}
