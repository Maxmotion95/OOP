package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.environment.PizzaOrder;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;
import ru.nsu.fit.lylova.staff.bakers.NormalBaker;
import ru.nsu.fit.lylova.staff.couriers.Courier;
import ru.nsu.fit.lylova.staff.couriers.LazyCourier;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Logger log = Logger.getLogger(Main.class.getName());
        log.info("logging started");

        PizzeriaOrderQueue orderQueue = new PizzeriaOrderQueue();
        PizzaWarehouse warehouse = new PizzaWarehouse(3);
        NormalBaker b1 = new NormalBaker(orderQueue, warehouse, "VASYA", log, 2000);
        b1.start();
        NormalBaker b2 = new NormalBaker(orderQueue, warehouse, "ANTON", log, 3000);
        b2.start();
        Courier c1 = new LazyCourier(5000, 3, warehouse, "OLEG", log);
        c1.start();

        Scanner sc = new Scanner(System.in);
        int pastOrderId = 0;
        while (true) {
            String s = sc.next();
            if (s.equals("softShutdown") || s.equals("s")) {
                b1.softShutdown();
                b2.softShutdown();
                b1.join();
                b2.join();
                c1.softShutdown();
                c1.join();
                break;
            } else if (s.equals("forceShutdown") || s.equals("f")) {
                b1.forceShutdown();
                b2.forceShutdown();
                c1.forceShutdown();
                b1.join();
                b2.join();
                c1.join();
                break;
            } else {
                orderQueue.addOrder(new PizzaOrder(pastOrderId));
                log.info("order " + pastOrderId + " accepted");
                pastOrderId++;
            }
        }
    }
}