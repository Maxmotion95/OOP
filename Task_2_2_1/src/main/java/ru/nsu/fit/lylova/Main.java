package ru.nsu.fit.lylova;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Logger log = Logger.getLogger(Main.class.getName());
        log.info("logging started");

        PizzeriaOrderQueue orderQueue = new PizzeriaOrderQueue();
        Baker b1 = new Baker(2000, orderQueue, "VASYA", log);
        b1.start();
        Baker b2 = new Baker(3000, orderQueue, "ANTON", log);
        b2.start();

        Scanner sc = new Scanner(System.in);
        int pastOrderId = 0;
        while (true) {
            String s = sc.next();
            if (s.equals("exit")) {
                b1.interrupt();
                b2.interrupt();
                b1.join();
                b2.join();
                break;
            } else {
                orderQueue.addOrder(new PizzaOrder(pastOrderId));
                pastOrderId++;
            }
        }
    }
}