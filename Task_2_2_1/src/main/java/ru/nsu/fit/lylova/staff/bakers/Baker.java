package ru.nsu.fit.lylova.staff.bakers;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;

import java.util.logging.Logger;

public class Baker extends Thread {
    private final long cooking_speed;
    private final PizzeriaOrderQueue orderQueue;
    private final PizzaWarehouse warehouse;
    private final String bakerName;
    private final Logger logger;
    private boolean isSoftShutdown = false;
    private boolean isForceShutdown = false;

    public Baker(long cooking_speed, PizzeriaOrderQueue orderQueue, PizzaWarehouse warehouse, String bakerName, Logger logger) {
        this.cooking_speed = cooking_speed;
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.bakerName = bakerName;
        this.logger = logger;
    }

    public void softShutdown() {
        isSoftShutdown = true;
        this.interrupt();
    }

    public void forceShutdown() {
        isForceShutdown = true;
        this.interrupt();
    }

    public void run() {
        this.startWork();
        PizzaOrder order = null;
        while (!isForceShutdown) {
            try {
                order = this.getOrderFromQueue();
            } catch (InterruptedException e) {
                continue;
            }

            if (order == null) {
                break;
            }

            // Baker received an order from the queue and starting to prepare it
            try {
                this.cookOrder(order);
            } catch (InterruptedException e) {
                if (isForceShutdown) {
                    break;
                }
                logger.info("order " + order.getOrderId() + " baked urgently by baker" + bakerName);
            }

            // add order to warehouse
            boolean isOrderAdded = false;
            while (!isOrderAdded) {
                try {
                    warehouse.addOrder(order);
                    isOrderAdded = true;
                    logger.info("order " + order.getOrderId() + " added to warehouse");
                } catch (InterruptedException e) {
                    if (isForceShutdown) {
                        break;
                    }
                }
            }
            if (!isOrderAdded) {
                break;
            }
        }
        this.endWork();
    }

    private void startWork() {
        logger.info("baker " + bakerName + " started working");
    }

    private PizzaOrder getOrderFromQueue() throws InterruptedException {
        if (isSoftShutdown) {
            return orderQueue.getOrderWithoutBlocking();
        }
        return orderQueue.getOrderWithBlocking();
    }

    private void cookOrder(PizzaOrder order) throws InterruptedException {
        logger.info("order " + order.getOrderId() + " started baking by baker" + bakerName);
        Thread.sleep(cooking_speed);
        logger.info("order " + order.getOrderId() + " baked by baker" + bakerName);
    }

    private void endWork() {
        if (isForceShutdown) {
            logger.info("baker " + bakerName + " ran away from work because of an emergency");
        } else {
            logger.info("baker " + bakerName + " ended work normally");
        }
    }
}
