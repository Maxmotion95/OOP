package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;
import ru.nsu.fit.lylova.services.BakerService;
import ru.nsu.fit.lylova.services.CourierService;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Class Pizzeria.
 */
public class Pizzeria {
    private final PizzeriaOrderQueue orderQueue;
    private final PizzaWarehouse warehouse;
    private final BakerService bakerService;
    private final CourierService courierService;
    private final Logger logger;

    /**
     * Creates a pizzeria with the provided parameters.
     *
     * @param warehouseCapacity  capacity of pizzeria warehouse
     * @param logger             logger
     * @param bakersConfigPath   path to bakers configuration file
     * @param couriersConfigPath path to couriers configuration file
     * @throws IOException if bakers or couriers configuration files is missing
     */
    public Pizzeria(int warehouseCapacity, Logger logger, String bakersConfigPath, String couriersConfigPath) throws IOException {
        this.orderQueue = new PizzeriaOrderQueue();
        this.warehouse = new PizzaWarehouse(warehouseCapacity);
        this.logger = logger;
        this.bakerService = new BakerService(orderQueue, warehouse, logger, bakersConfigPath);
        this.courierService = new CourierService(warehouse, logger, couriersConfigPath);
    }

    /**
     * Starts work of pizzeria.
     */
    public void startWork() {
        bakerService.startAll();
        courierService.startAll();
    }

    /**
     * Finishes the work of the pizzeria with the completion of all orders.
     *
     * @throws InterruptedException if thread with pizzeria was interrupted
     */
    public void softShutdown() throws InterruptedException {
        bakerService.softShutdownAll();
        bakerService.joinAll();
        courierService.softShutdownAll();
        courierService.joinAll();
    }

    /**
     * Finishes the pizzeria's work urgently without completing all orders.
     *
     * @throws InterruptedException if thread with pizzeria was interrupted
     */
    public void forceShutdown() throws InterruptedException {
        bakerService.forceShutdownAll();
        courierService.forceShutdownAll();
        bakerService.joinAll();
        courierService.joinAll();
    }

    /**
     * Adds an order to the order queue of pizzeria.
     *
     * @param order new order
     */
    public void addOrder(PizzaOrder order) {
        orderQueue.addOrder(order);
        logger.info("order " + order.getOrderId() + " accepted");
    }

    /**
     * Returns order queue of pizzeria.
     *
     * @return pizzeria's order queue
     */
    public PizzeriaOrderQueue getOrderQueue() {
        return this.orderQueue;
    }

    /**
     * Returns warehouse of pizzeria.
     *
     * @return pizzeria's warehouse
     */
    public PizzaWarehouse getWarehouse() {
        return this.warehouse;
    }
}
