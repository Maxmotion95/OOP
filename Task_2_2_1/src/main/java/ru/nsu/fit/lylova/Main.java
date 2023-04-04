package ru.nsu.fit.lylova;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;
import ru.nsu.fit.lylova.environment.PizzaOrder;

/**
 * Class Main with method main, that starts pizzeria.
 */
public class Main {
    /**
     * Method main, that starts pizzeria. <br>
     * Interaction protocol:
     * <ul>
     *     <li>
     *         "softShutdown" or "s" - the pizzeria closes completing all orders.
     *     </li>
     *     <li>
     *         "forceShutdown" or "f" - pizzeria closes urgently without completing all orders.
     *     </li>
     *     <li>
     *          any other token - a new order is being created.
     *     </li>
     * </ul>
     *
     * @param args arguments command line (not used)
     * @throws IOException if the pizzeria configuration files are missing
     */
    public static void main(String[] args) throws IOException {
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
                try {
                    pizzeria.softShutdown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            } else if (s.equals("forceShutdown") || s.equals("f")) {
                try {
                    pizzeria.forceShutdown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            } else {
                pizzeria.addOrder(new PizzaOrder(pastOrderId));
                pastOrderId++;
            }
        }
    }
}