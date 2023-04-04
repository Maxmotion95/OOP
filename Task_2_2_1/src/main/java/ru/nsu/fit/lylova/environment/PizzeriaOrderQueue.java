package ru.nsu.fit.lylova.environment;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Pizzeria order queue class.
 */
public class PizzeriaOrderQueue {
    private final Queue<PizzaOrder> ordersQueue = new LinkedList<>();

    /**
     * Creates empty order queue.
     */
    public PizzeriaOrderQueue() {
    }

    /**
     * Returns number of orders in order queue.
     *
     * @return number of orders in order queue
     */
    public int getOrdersCount() {
        synchronized (this) {
            return ordersQueue.size();
        }
    }

    /**
     * Deletes and returns order.
     * Blocks until the thread is interrupted or a new order appears in the order queue.
     *
     * @return pizza order
     * @throws InterruptedException if thread was interrupted
     */
    public PizzaOrder getOrderWithBlocking() throws InterruptedException {
        synchronized (this) {
            if (!ordersQueue.isEmpty()) {
                return ordersQueue.poll();
            }
            this.wait();
            return ordersQueue.poll();
        }
    }

    /**
     * Deletes and returns order.
     * If order queue is empty, it immediately returns {@code null}.
     *
     * @return pizza order or {@code null} if order queue empty.
     */
    public PizzaOrder getOrderWithoutBlocking() {
        synchronized (this) {
            return ordersQueue.poll();
        }
    }

    /**
     * Adds an order to the order queue.
     *
     * @param order pizza order
     */
    public void addOrder(PizzaOrder order) {
        synchronized (this) {
            ordersQueue.add(order);
            this.notify();
        }
    }
}
