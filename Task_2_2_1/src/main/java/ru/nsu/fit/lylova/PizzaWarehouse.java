package ru.nsu.fit.lylova;

import java.util.LinkedList;
import java.util.Queue;

public class PizzaWarehouse {
    private final Queue<PizzaOrder> ordersQueue = new LinkedList<>();
    private final int capacity;

    PizzaWarehouse(int capacity) {
        this.capacity = capacity;
    }

    public int getOrdersCount() {
        synchronized (this) {
            return ordersQueue.size();
        }
    }

    public void addOrder(PizzaOrder order) throws InterruptedException {
        synchronized (this) {
            while (ordersQueue.size() >= capacity) {
                this.wait();
            }
            ordersQueue.add(order);
            this.notifyAll();
        }
    }

    public PizzaOrder getOrderWithBlocking() throws InterruptedException {
        synchronized (this) {
            while (ordersQueue.isEmpty()) {
                this.wait();
            }
            return ordersQueue.poll();
        }
    }

    public PizzaOrder getOrderWithoutBlocking() {
        synchronized (this) {
            return ordersQueue.poll();
        }
    }
}
