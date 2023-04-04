package ru.nsu.fit.lylova.environment;

/**
 * Interface baker warehouse.
 */
public interface PizzaWarehouseForBaker {
    /**
     * Adds an order to the warehouse.
     *
     * @param order pizza order
     * @throws InterruptedException if thread was interrupted
     */
    void addOrder(PizzaOrder order) throws InterruptedException;
}
