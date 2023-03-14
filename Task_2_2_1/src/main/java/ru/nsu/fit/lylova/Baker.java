package ru.nsu.fit.lylova;

import java.util.logging.Logger;

public class Baker extends Thread {
    private final long cooking_speed;
    private final PizzeriaOrderQueue orderQueue;
    private final String bakerName;
    private final Logger logger;

    Baker(long cooking_speed, PizzeriaOrderQueue orderQueue, String bakerName, Logger logger) {
        this.cooking_speed = cooking_speed;
        this.orderQueue = orderQueue;
        this.bakerName = bakerName;
        this.logger = logger;
    }

    public void run() {
        logger.info("baker " + bakerName + " started working");
        PizzaOrder order = null;
        boolean isEndOfWork = false;
        while (true) {
            isEndOfWork |= currentThread().isInterrupted();
            synchronized (orderQueue) {
                if (isEndOfWork && orderQueue.getOrdersCount() == 0) {
                    break;
                }
                order = orderQueue.getOrder();
                if (order == null) {
                    try {
                        orderQueue.wait();
                        order = orderQueue.getOrder();
                    } catch (InterruptedException e) {
                        isEndOfWork = true;
                        continue;
                    }
                }
            }
            // Baker received an order from the queue and starting to prepare it
            logger.info("order " + order.getOrderId() + " started baking by baker" + bakerName);
            try {
                Thread.sleep(cooking_speed);
                logger.info("order " + order.getOrderId() + " baked by baker" + bakerName);
            } catch (InterruptedException e) {
                logger.info("order " + order.getOrderId() + " baked urgently by baker" + bakerName);
                isEndOfWork = true;
            }
        }
        logger.info("baker " + bakerName + " finished the job");
    }
}
