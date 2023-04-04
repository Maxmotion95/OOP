package ru.nsu.fit.lylova.staff.couriers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;
import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouseForCourier;

/**
 * Abstract class Courier.
 */
public abstract class Courier extends Thread {
    /**
     * Pizzeria warehouse.
     */
    protected final PizzaWarehouseForCourier warehouse;
    /**
     * Courier name.
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
     * Creates courier with specified parameters.
     *
     * @param warehouse pizzeria warehouse
     * @param name      courier name
     * @param logger    logger
     */
    protected Courier(PizzaWarehouseForCourier warehouse, String name, Logger logger) {
        this.warehouse = warehouse;
        this.name = name;
        this.logger = logger;
    }

    /**
     * Tells the courier that he can finish the job if the order queue is empty.
     */
    public void softShutdown() {
        isSoftShutdown = true;
        this.interrupt();
    }

    /**
     * Tells the courier that he can finish the job as soon as possible.
     */
    public void forceShutdown() {
        isForceShutdown = true;
        this.interrupt();
    }


    /**
     * Starts work of courier.
     */
    public void run() {
        this.startWork();
        Queue<PizzaOrder> trunk = new LinkedList<>();
        while (!isForceShutdown) {
            boolean isTrunkFilled = false;
            while (!isTrunkFilled) {
                try {
                    this.fillTrunk(trunk);
                    isTrunkFilled = true;
                } catch (InterruptedException e) {
                    if (isForceShutdown) {
                        break;
                    }
                }
            }
            if (isForceShutdown) {
                break;
            }
            if (isSoftShutdown && trunk.isEmpty()) {
                break;
            }

            this.deliverOrdersFromTrunk(trunk);
        }
        this.endWork();
    }

    /**
     * A method that describes how a courier goes to work.
     */
    protected abstract void startWork();

    /**
     * A method that describes how a courier fills his trunk.
     *
     * @param trunk courier's car trunk
     * @throws InterruptedException if thread was interrupted
     */
    protected abstract void fillTrunk(Queue<PizzaOrder> trunk) throws InterruptedException;

    /**
     * A method that describes how a courier deliver orders from his trunk.
     *
     * @param trunk courier's car trunk
     */
    protected abstract void deliverOrdersFromTrunk(Queue<PizzaOrder> trunk);

    /**
     * A method that describes how a courier ends his work.
     */
    protected abstract void endWork();
}
