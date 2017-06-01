package com.example.demo.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository {
    Optional<Order> ofId(String id);

    List<Order> all();
}
