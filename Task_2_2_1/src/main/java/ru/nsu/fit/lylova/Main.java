package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;
import ru.nsu.fit.lylova.services.BakerService;
import ru.nsu.fit.lylova.services.CourierService;
import ru.nsu.fit.lylova.staff.bakers.NormalBaker;
import ru.nsu.fit.lylova.staff.couriers.Courier;
import ru.nsu.fit.lylova.staff.couriers.LazyCourier;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Logger log = Logger.getLogger(Main.class.getName());
        log.info("logging started");

        PizzeriaOrderQueue orderQueue = new PizzeriaOrderQueue();
        PizzaWarehouse warehouse = new PizzaWarehouse(3);

        CourierService courierService = new CourierService(warehouse, log, "Task_2_2_1/src/main/resources/couriersConfig.json");
        BakerService bakerService = new BakerService(orderQueue, warehouse, log, "Task_2_2_1/src/main/resources/bakersConfig.json");

        bakerService.startAll();
        courierService.startAll();

        Scanner sc = new Scanner(System.in);
        int pastOrderId = 0;
        while (true) {
            String s = sc.next();
            if (s.equals("softShutdown") || s.equals("s")) {
                bakerService.softShutdownAll();
                bakerService.joinAll();
                courierService.softShutdownAll();
                courierService.joinAll();
                break;
            } else if (s.equals("forceShutdown") || s.equals("f")) {
                bakerService.forceShutdownAll();
                courierService.forceShutdownAll();
                bakerService.joinAll();
                courierService.joinAll();
                break;
            } else {
                orderQueue.addOrder(new PizzaOrder(pastOrderId));
                log.info("order " + pastOrderId + " accepted");
                pastOrderId++;
            }
        }
    }
}