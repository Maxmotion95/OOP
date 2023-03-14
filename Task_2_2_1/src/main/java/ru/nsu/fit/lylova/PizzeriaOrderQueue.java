package ru.nsu.fit.lylova;

import java.util.LinkedList;
import java.util.Queue;

public class PizzeriaOrderQueue {
    final Queue<PizzaOrder> ordersQueue = new LinkedList<>();

    PizzeriaOrderQueue() {
    }

    public int getOrdersCount() {
        return ordersQueue.size();

    }

    public PizzaOrder getOrder() {
        return ordersQueue.poll();
    }

    void addOrder(PizzaOrder order) {
        ordersQueue.add(order);
        this.notify();
    }
}
