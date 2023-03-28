package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;
import ru.nsu.fit.lylova.services.BakerService;
import ru.nsu.fit.lylova.services.CourierService;

import java.io.IOException;
import java.util.logging.Logger;

public class Pizzeria {
    private final PizzeriaOrderQueue orderQueue;
    private final PizzaWarehouse warehouse;
    private final BakerService bakerService;
    private final CourierService courierService;
    private final Logger logger;

    public Pizzeria(int warehouseCapacity, Logger logger, String bakersConfigPath, String couriersConfigPath) throws IOException {
        this.orderQueue = new PizzeriaOrderQueue();
        this.warehouse = new PizzaWarehouse(warehouseCapacity);
        this.logger = logger;
        this.bakerService = new BakerService(orderQueue, warehouse, logger, bakersConfigPath);
        this.courierService = new CourierService(warehouse, logger, couriersConfigPath);
    }

    public void startWork() {
        bakerService.startAll();
        courierService.startAll();
    }

    public void softShutdown() throws InterruptedException {
        bakerService.softShutdownAll();
        bakerService.joinAll();
        courierService.softShutdownAll();
        courierService.joinAll();
    }

    public void forceShutdown() throws InterruptedException {
        bakerService.forceShutdownAll();
        courierService.forceShutdownAll();
        bakerService.joinAll();
        courierService.joinAll();
    }

    public void addOrder(PizzaOrder order) {
        orderQueue.addOrder(order);
        logger.info("order " + order.getOrderId() + " accepted");
    }
}
