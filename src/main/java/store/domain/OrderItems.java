package store.domain;

import java.util.List;

public class OrderItems {

    private final List<OrderItem> orderItems;

    public OrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
