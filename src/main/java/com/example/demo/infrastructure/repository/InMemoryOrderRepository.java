package com.example.demo.infrastructure.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

@Component
public class InMemoryOrderRepository implements OrderRepository {
    private Map<String, Order> store;

    public InMemoryOrderRepository() {
        store = new HashMap<>();
        Order order = new Order("123", asList(new Order.LineItem("product1", 12.99f, 1)));
        store.put(order.getId(), order);
    }

    @Override
    public Optional<Order> ofId(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Order> all() {
        return new ArrayList<>(store.values());
    }
}
