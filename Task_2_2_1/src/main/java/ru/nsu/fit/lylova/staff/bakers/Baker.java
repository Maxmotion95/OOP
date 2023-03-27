package ru.nsu.fit.lylova.staff.bakers;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;

import java.util.logging.Logger;

public abstract class Baker extends Thread {
    protected final PizzeriaOrderQueue orderQueue;
    protected final PizzaWarehouse warehouse;
    protected final String bakerName;
    protected final Logger logger;
    protected boolean isSoftShutdown = false;
    protected boolean isForceShutdown = false;

    protected Baker(PizzeriaOrderQueue orderQueue, PizzaWarehouse warehouse, String bakerName, Logger logger) {
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

    protected abstract void startWork();

    protected abstract PizzaOrder getOrderFromQueue() throws InterruptedException;

    protected abstract void cookOrder(PizzaOrder order) throws InterruptedException;

    protected abstract void endWork();
}
