package ru.nsu.fit.lylova.staff.bakers;

import java.util.logging.Logger;
import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouseForBaker;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;

/**
 * Abstract class Baker.
 */
public abstract class Baker extends Thread {
    /**
     * Pizzeria order queue.
     */
    protected final PizzeriaOrderQueue orderQueue;
    /**
     * Pizzeria warehouse.
     */
    protected final PizzaWarehouseForBaker warehouse;
    /**
     * Baker name.
     */
    protected final String name;
    /**
     * Logger.
     */
    protected final Logger logger;
    /**
     * Flag for softShutdown.
     */
    protected boolean isSoftShutdown = false;
    /**
     * Flag for forceShutdown.
     */
    protected boolean isForceShutdown = false;

    /**
     * Creates baker with specified parameters.
     *
     * @param orderQueue pizzeria order queue
     * @param warehouse pizzeria warehouse
     * @param name baker name
     * @param logger logger
     */
    protected Baker(PizzeriaOrderQueue orderQueue,
                    PizzaWarehouseForBaker warehouse,
                    String name,
                    Logger logger) {
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.name = name;
        this.logger = logger;
    }

    /**
     * Tells the baker that he can finish the job if the order queue is empty.
     */
    public void softShutdown() {
        isSoftShutdown = true;
        this.interrupt();
    }

    /**
     * Tells the baker that he can finish the job as soon as possible.
     */
    public void forceShutdown() {
        isForceShutdown = true;
        this.interrupt();
    }

    /**
     * Starts work of baker.
     */
    public void run() {
        this.startWork();
        PizzaOrder order;
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
                logger.info("order " + order.getOrderId() + " baked urgently by baker" + name);
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

    /**
     * A method that describes how a baker goes to work.
     */
    protected abstract void startWork();

    /**
     * A method that describes how a baker picks up orders from a queue.
     *
     * @return PizzaOrder pizza order
     * @throws InterruptedException if thread was interrupted
     */
    protected abstract PizzaOrder getOrderFromQueue() throws InterruptedException;

    /**
     * A method that describes how a baker bakes an order.
     *
     * @param order pizza order
     * @throws InterruptedException if thread was interrupted
     */
    protected abstract void cookOrder(PizzaOrder order) throws InterruptedException;

    /**
     * A method that describes how a baker ends work.
     */
    protected abstract void endWork();
}
