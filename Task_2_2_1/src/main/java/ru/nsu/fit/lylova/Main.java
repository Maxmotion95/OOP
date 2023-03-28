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

        Pizzeria pizzeria = new Pizzeria(
                3,
                log,
                "Task_2_2_1/src/main/resources/bakersConfig.json",
                "Task_2_2_1/src/main/resources/couriersConfig.json"
        );

        pizzeria.startWork();

        Scanner sc = new Scanner(System.in);
        int pastOrderId = 0;
        while (true) {
            String s = sc.next();
            if (s.equals("softShutdown") || s.equals("s")) {
                pizzeria.softShutdown();
                break;
            } else if (s.equals("forceShutdown") || s.equals("f")) {
                pizzeria.forceShutdown();
                break;
            } else {
                pizzeria.addOrder(new PizzaOrder(pastOrderId));
                pastOrderId++;
            }
        }
    }
}