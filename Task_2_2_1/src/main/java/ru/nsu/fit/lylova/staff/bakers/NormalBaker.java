package ru.nsu.fit.lylova.staff.bakers;

import java.util.logging.Logger;
import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;

/**
 * Class of normal baker, which takes one order from the queue and bakes it for a certain time.
 */
public class NormalBaker extends Baker {
    private final long cooking_speed;

    /**
     * Creates baker with specified parameters.
     *
     * @param orderQueue    pizzeria order queue
     * @param warehouse     pizzeria warehouse
     * @param bakerName     baker name
     * @param logger        logger
     * @param cooking_speed baking time of one order in milliseconds
     */
    public NormalBaker(PizzeriaOrderQueue orderQueue, PizzaWarehouse warehouse, String bakerName, Logger logger, long cooking_speed) {
        super(orderQueue, warehouse, bakerName, logger);
        this.cooking_speed = cooking_speed;
    }


    protected void startWork() {
        logger.info("baker " + name + " started working");
    }

    protected PizzaOrder getOrderFromQueue() throws InterruptedException {
        if (isSoftShutdown) {
            return orderQueue.getOrderWithoutBlocking();
        }
        return orderQueue.getOrderWithBlocking();
    }

    protected void cookOrder(PizzaOrder order) throws InterruptedException {
        logger.info("order " + order.getOrderId() + " started baking by baker" + name);
        Thread.sleep(cooking_speed);
        logger.info("order " + order.getOrderId() + " baked by baker" + name);
    }

    protected void endWork() {
        if (isForceShutdown) {
            logger.info("baker " + name + " ran away from work because of an emergency");
        } else {
            logger.info("baker " + name + " ended work normally");
        }
    }
}
