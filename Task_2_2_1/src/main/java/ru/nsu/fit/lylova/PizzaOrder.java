package ru.nsu.fit.lylova;

public class PizzaOrder {
    private final int orderId;
    PizzaOrder(int orderId) {
        this.orderId = orderId;
    }

    int getOrderId() {
        return this.orderId;
    }
}
