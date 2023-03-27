package ru.nsu.fit.lylova.environment;

public class PizzaOrder {
    private final int orderId;
    public PizzaOrder(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return this.orderId;
    }
}
