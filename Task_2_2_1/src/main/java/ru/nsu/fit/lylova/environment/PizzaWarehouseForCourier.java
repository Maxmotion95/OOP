package ru.nsu.fit.lylova.environment;

/**
 * Interface courier warehouse.
 */
public interface PizzaWarehouseForCourier {
    /**
     * Deletes and returns order.
     * Blocks until the thread is interrupted or a new order appears in the warehouse.
     *
     * @return pizza order
     * @throws InterruptedException if thread was interrupted
     */
    PizzaOrder getOrderWithBlocking() throws InterruptedException;

    /**
     * Deletes and returns order.
     * If warehouse is empty, it immediately returns {@code null}.
     *
     * @return pizza order or {@code null} if warehouse empty.
     */
    PizzaOrder getOrderWithoutBlocking();
}
