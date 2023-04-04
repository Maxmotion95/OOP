package ru.nsu.fit.lylova.environment;

/**
 * Class pizza order.
 */
public class PizzaOrder {
    private final int orderId;

    /**
     * Creates pizza order with specified ID.
     *
     * @param orderId order ID
     */
    public PizzaOrder(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns order ID of order.
     *
     * @return order ID
     */
    public int getOrderId() {
        return this.orderId;
    }
}
