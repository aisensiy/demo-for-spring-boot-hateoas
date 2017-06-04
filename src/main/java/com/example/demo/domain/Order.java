package com.example.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {
    private String id;
    private Status status;
    private List<LineItem> items;

    public Order(String id, List<LineItem> items) {
        this.id = id;
        this.items = items;
        status = Status.CREATED;
    }

    public void pay() {
        if (status != Status.CREATED) {
            throw new IllegalStateException("Only new order can be paid");
        }
        this.status = Status.PAID;
    }

    public boolean paid() {
        return status == Status.PAID;
    }

    @Value
    public static class LineItem {
        private String productId;
        private double price;
        private int amount;
    }

    public enum Status {
        CREATED, PAID, CANCELLED, FINISHED
    }
}
