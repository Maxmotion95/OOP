package ru.nsu.fit.lylova.staff.couriers;

import java.util.Queue;
import java.util.logging.Logger;
import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;

/**
 * Class of active courier, which takes the maximum number of orders that he can take now
 * and immediately goes to deliver them.
 */
public class ActiveCourier extends Courier {
    private final long deliveryTime;
    private final int trunkSize;

    public ActiveCourier(PizzaWarehouse warehouse, String name, Logger logger,
                         long deliveryTime, int trunkSize) {
        super(warehouse, name, logger);
        this.deliveryTime = deliveryTime;
        this.trunkSize = trunkSize;
    }

    @Override
    protected void startWork() {
        logger.info("active courier " + name + " started working");
    }

    @Override
    protected void fillTrunk(Queue<PizzaOrder> trunk) throws InterruptedException {
        PizzaOrder order;
        if (isSoftShutdown) {
            order = warehouse.getOrderWithoutBlocking();
            if (order == null) {
                return;
            }
        } else {
            order = warehouse.getOrderWithBlocking();
        }
        trunk.add(order);
        logger.info("order " + order.getOrderId() + " taken for delivery by active courier " + name);

        while (trunk.size() < trunkSize && order != null) {
            order = warehouse.getOrderWithoutBlocking();
            if (order != null) {
                trunk.add(order);
                logger.info("order " + order.getOrderId() + " taken for delivery by active courier " + name);
            }
        }
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
                logger.info("order " + order.getOrderId() + " delivered by active courier " + name);
            } else {
                logger.info(
                        "order "
                                + order.getOrderId()
                                + " delivered urgently by active courier "
                                + name
                );
            }
        }
    }

    @Override
    protected void endWork() {
        logger.info("active courier " + name + " finished the job");
    }
}
