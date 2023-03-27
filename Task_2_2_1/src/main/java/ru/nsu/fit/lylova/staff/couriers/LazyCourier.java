package ru.nsu.fit.lylova.staff.couriers;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;

import java.util.Queue;
import java.util.logging.Logger;

public class LazyCourier extends Courier {
    private final long deliveryTime;
    private final int trunkSize;

    public LazyCourier(PizzaWarehouse warehouse, String name, Logger logger, long deliveryTime, int trunkSize) {
        super(warehouse, name, logger);
        this.deliveryTime = deliveryTime;
        this.trunkSize = trunkSize;
    }

    @Override
    protected void fillTrunk(Queue<PizzaOrder> trunk) throws InterruptedException {
        while (trunk.size() < trunkSize) {
            PizzaOrder order;
            if (isSoftShutdown) {
                order = super.warehouse.getOrderWithoutBlocking();
            } else {
                order = warehouse.getOrderWithBlocking();
            }
            if (order == null) {
                break;
            }
            trunk.add(order);
            logger.info("order " + order.getOrderId() + " taken for delivery by lazy courier " + name);
        }
    }

    @Override
    protected void startWork() {
        logger.info("lazy courier " + name + " started working");
    }

    @Override
    protected void deliverOrdersFromTrunk(Queue<PizzaOrder> trunk) {
        boolean normalDelivery = true;
        try {
            Thread.sleep(deliveryTime);
        } catch (InterruptedException e) {
            if (isForceShutdown) {
                return;
            }
            normalDelivery = false;
        }
        while (!trunk.isEmpty()) {
            PizzaOrder order = trunk.poll();
            if (normalDelivery) {
                logger.info("order " + order.getOrderId() + " delivered by lazy courier " + name);
            } else {
                logger.info("order " + order.getOrderId() + " delivered urgently by lazy courier " + name);
            }
        }
    }

    @Override
    protected void endWork() {
        logger.info("lazy courier " + name + " finished the job");
    }
}
