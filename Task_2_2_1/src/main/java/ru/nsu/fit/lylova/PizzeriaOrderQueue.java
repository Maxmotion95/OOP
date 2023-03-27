package ru.nsu.fit.lylova;

import java.util.LinkedList;
import java.util.Queue;

public class PizzeriaOrderQueue {
    private final Queue<PizzaOrder> ordersQueue = new LinkedList<>();

    PizzeriaOrderQueue() {
    }

    public int getOrdersCount() {
        synchronized (this) {
            return ordersQueue.size();
        }
    }

    public PizzaOrder getOrderWithBlocking() throws InterruptedException {
        synchronized (this) {
            if (!ordersQueue.isEmpty()) {
                return ordersQueue.poll();
            }
            this.wait();
            return ordersQueue.poll();
        }
    }

    public PizzaOrder getOrderWithoutBlocking() {
        synchronized (this) {
            return ordersQueue.poll();
        }
    }

    void addOrder(PizzaOrder order) {
        synchronized (this) {
            ordersQueue.add(order);
            this.notify();
        }
    }
}
