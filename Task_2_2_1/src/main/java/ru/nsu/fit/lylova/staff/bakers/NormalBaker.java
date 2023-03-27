package ru.nsu.fit.lylova.staff.bakers;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;

import java.util.logging.Logger;

public class NormalBaker extends Baker {
    private final long cooking_speed;

    public NormalBaker(PizzeriaOrderQueue orderQueue, PizzaWarehouse warehouse, String bakerName, Logger logger, long cooking_speed) {
        super(orderQueue, warehouse, bakerName, logger);
        this.cooking_speed = cooking_speed;
    }


    protected void startWork() {
        logger.info("baker " + bakerName + " started working");
    }

    protected PizzaOrder getOrderFromQueue() throws InterruptedException {
        if (isSoftShutdown) {
            return orderQueue.getOrderWithoutBlocking();
        }
        return orderQueue.getOrderWithBlocking();
    }

    protected void cookOrder(PizzaOrder order) throws InterruptedException {
        logger.info("order " + order.getOrderId() + " started baking by baker" + bakerName);
        Thread.sleep(cooking_speed);
        logger.info("order " + order.getOrderId() + " baked by baker" + bakerName);
    }

    protected void endWork() {
        if (isForceShutdown) {
            logger.info("baker " + bakerName + " ran away from work because of an emergency");
        } else {
            logger.info("baker " + bakerName + " ended work normally");
        }
    }
}
