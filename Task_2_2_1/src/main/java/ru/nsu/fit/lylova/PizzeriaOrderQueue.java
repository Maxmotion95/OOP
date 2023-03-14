package ru.nsu.fit.lylova;

import java.util.LinkedList;
import java.util.Queue;

public class PizzeriaOrderQueue {
    final Queue<PizzaOrder> ordersQueue = new LinkedList<>();

    PizzeriaOrderQueue() {
    }

    public int getOrdersCount() {
        synchronized (ordersQueue) {
            return ordersQueue.size();
        }
    }
    public PizzaOrder getOrder() throws InterruptedException {
        synchronized (ordersQueue) {
            if (ordersQueue.isEmpty() && !Thread.currentThread().isInterrupted()) {
                ordersQueue.wait();
            }
            return ordersQueue.poll();
        }

    }

    void addOrder(PizzaOrder order) {
        synchronized (ordersQueue) {
            ordersQueue.add(order);
            ordersQueue.notify();
        }
    }
}
