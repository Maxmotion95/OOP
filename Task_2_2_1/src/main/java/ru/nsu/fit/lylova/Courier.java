package ru.nsu.fit.lylova;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class Courier extends Thread {
    private final long deliveryTime;
    private final int trunkSize;
    private final PizzeriaOrderQueue orderQueue;
    private final PizzaWarehouse warehouse;
    private final String name;
    private final Logger logger;

    Courier(long deliveryTime, int trunkSize, PizzeriaOrderQueue orderQueue, PizzaWarehouse warehouse, String name, Logger logger) {
        this.deliveryTime = deliveryTime;
        this.trunkSize = trunkSize;
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.name = name;
        this.logger = logger;
    }

    private void fillTrunk(Queue<PizzaOrder> trunk) {
        while (trunk.size() < trunkSize && warehouse.getOrdersCount() != 0) {
            PizzaOrder order = warehouse.getOrder();
            trunk.add(order);
            logger.info("order " + order.getOrderId() + " taken for delivery by courier " + name);
        }
    }

    public void run() {
        logger.info("courier " + name + " started working");
        Queue<PizzaOrder> trunk = new LinkedList<>();
        boolean isEndOfWork = false;
        while (true) {
            isEndOfWork |= currentThread().isInterrupted();
            synchronized (warehouse) {
                if (isEndOfWork && warehouse.getOrdersCount() == 0) {
                    break;
                }
                fillTrunk(trunk);
                while (trunk.size() == 0) {
                    try {
                        warehouse.wait();
                        fillTrunk(trunk);
                    } catch (InterruptedException e) {
                        isEndOfWork = true;
                        break;
                    }
                }
                if (trunk.size() == 0) {
                    continue;
                }
                warehouse.notifyAll();
            }

            boolean normalDelivery = true;
            try {
                Thread.sleep(deliveryTime);
            } catch (InterruptedException e) {
                normalDelivery = false;
                isEndOfWork = true;
            }
            while (trunk.size() != 0) {
                PizzaOrder order = trunk.poll();
                if (normalDelivery) {
                    logger.info("order " + order.getOrderId() + " delivered by courier " + name);
                } else {
                    logger.info("order " + order.getOrderId() + " delivered urgently by courier " + name);
                }
            }

        }
        logger.info("courier " + name + " finished the job");
    }
}
