package ru.nsu.fit.lylova.environment;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Pizzeria warehouse class.
 * Orders made by bakers, but not taken for delivery by couriers, are stored in the warehouse.
 */
public class PizzaWarehouse implements PizzaWarehouseForBaker, PizzaWarehouseForCourier {
    private final Queue<PizzaOrder> ordersQueue = new LinkedList<>();
    private final int capacity;

    /**
     * Creates warehouse with specified capacity.
     *
     * @param capacity maximum number of orders that can fit into the warehouse
     */
    public PizzaWarehouse(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns number of orders in warehouse.
     *
     * @return number of orders in warehouse
     */
    public int getOrdersCount() {
        synchronized (this) {
            return ordersQueue.size();
        }
    }

    /**
     * Adds an order to the warehouse.
     *
     * @param order pizza order
     * @throws InterruptedException if thread was interrupted
     */
    public void addOrder(PizzaOrder order) throws InterruptedException {
        synchronized (this) {
            while (ordersQueue.size() >= capacity) {
                this.wait();
            }
            ordersQueue.add(order);
            this.notifyAll();
        }
    }

    /**
     * Deletes and returns order.
     * Blocks until the thread is interrupted or a new order appears in the warehouse.
     *
     * @return pizza order
     * @throws InterruptedException if thread was interrupted
     */
    public PizzaOrder getOrderWithBlocking() throws InterruptedException {
        synchronized (this) {
            while (ordersQueue.isEmpty()) {
                this.wait();
            }
            return ordersQueue.poll();
        }
    }

    /**
     * Deletes and returns order.
     * If warehouse is empty, it immediately returns {@code null}.
     *
     * @return pizza order or {@code null} if warehouse empty.
     */
    public PizzaOrder getOrderWithoutBlocking() {
        synchronized (this) {
            return ordersQueue.poll();
        }
    }
}
