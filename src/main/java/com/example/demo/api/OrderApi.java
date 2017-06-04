package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderApi {
    private OrderRepository orderRepository;

    @Autowired
    public OrderApi(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        Resources<OrderResource> resources = new Resources<>(
            orderRepository
                .all()
                .stream()
                .map(OrderResource::new)
                .collect(Collectors.toList()));
        resources.add(linkTo(methodOn(OrderApi.class).all()).withSelfRel());
        return ResponseEntity.ok(resources);
    }

    @RequestMapping(path = "{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> orderResource(@PathVariable("orderId") String orderId) {
        Order order = orderRepository.ofId(orderId).get();
        return ResponseEntity.ok(new OrderResource(order));
    }

    @RequestMapping(path = "{orderId}/payment", method = RequestMethod.PUT)
    public ResponseEntity pay(@PathVariable("orderId") String orderId) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

