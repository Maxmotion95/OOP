package ru.nsu.fit.lylova;

import java.util.LinkedList;
import java.util.Queue;

public class PizzaWarehouse {

    public final Object warehouseForBakers = new Object();
    public final Object warehouseForCourier = new Object();
    private final Queue<PizzaOrder> ordersQueue = new LinkedList<>();
    private final int capacity;

    PizzaWarehouse(int capacity) {
        this.capacity = capacity;
    }

    public int getOrdersCount() {
        return ordersQueue.size();
    }

    public boolean addOrder(PizzaOrder order) {
        if (ordersQueue.size() >= capacity) {
            return false;
        }
        ordersQueue.add(order);
        return true;
    }

    public PizzaOrder getOrder() {
        return ordersQueue.poll();
    }
}
