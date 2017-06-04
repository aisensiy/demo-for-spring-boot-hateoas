package com.example.demo.api;

import com.example.demo.domain.Order;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class OrderResource extends Resource<Order> {
    public OrderResource(Order order) {
        super(order);

        this.add(
            linkTo(methodOn(OrderApi.class).orderResource(order.getId()))
                .withSelfRel());

        if (!order.paid()) {
            this.add(
                linkTo(methodOn(OrderApi.class).pay(order.getId()))
                    .withRel("payment"));
        }
    }
}
