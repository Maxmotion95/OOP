package ru.nsu.fit.lylova;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.lylova.environment.PizzaOrder;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {

    public Pizzeria pizzeria;

    @BeforeEach
    public void init() throws IOException {
        Logger log = Logger.getLogger(Main.class.getName());
        pizzeria = new Pizzeria(
                3,
                log,
                "src/test/resources/bakersConfig.json",
                "src/test/resources/couriersConfig.json"
        );
    }

    @Test
    void softShutdown() throws InterruptedException {
        pizzeria.startWork();
        pizzeria.addOrder(new PizzaOrder(100));
        pizzeria.addOrder(new PizzaOrder(200));
        pizzeria.softShutdown();
        assertEquals(0, pizzeria.getOrderQueue().getOrdersCount());
        assertEquals(0, pizzeria.getWarehouse().getOrdersCount());
    }

    @Test
    void forceShutdown() throws InterruptedException, IOException {
        Logger log = Logger.getLogger(Main.class.getName());
        pizzeria = new Pizzeria(
                3,
                log,
                "src/test/resources/bakersConfigEmpty.json",
                "src/test/resources/couriersConfig.json"
        );
        pizzeria.startWork();
        pizzeria.addOrder(new PizzaOrder(100));
        pizzeria.addOrder(new PizzaOrder(200));
        pizzeria.addOrder(new PizzaOrder(300));
        pizzeria.addOrder(new PizzaOrder(400));
        pizzeria.addOrder(new PizzaOrder(500));
        pizzeria.addOrder(new PizzaOrder(600));
        pizzeria.addOrder(new PizzaOrder(700));
        pizzeria.addOrder(new PizzaOrder(800));
        Thread.sleep(10000);
        pizzeria.forceShutdown();
        assertEquals(8, pizzeria.getOrderQueue().getOrdersCount());
        assertEquals(0, pizzeria.getWarehouse().getOrdersCount());

        pizzeria = new Pizzeria(
                3,
                log,
                "src/test/resources/bakersConfig.json",
                "src/test/resources/couriersConfigEmpty.json"
        );
        pizzeria.startWork();
        pizzeria.addOrder(new PizzaOrder(100));
        pizzeria.addOrder(new PizzaOrder(200));
        pizzeria.addOrder(new PizzaOrder(300));
        pizzeria.addOrder(new PizzaOrder(400));
        pizzeria.addOrder(new PizzaOrder(500));
        pizzeria.addOrder(new PizzaOrder(600));
        pizzeria.addOrder(new PizzaOrder(700));
        pizzeria.addOrder(new PizzaOrder(800));
        Thread.sleep(10000);
        pizzeria.forceShutdown();
        assertEquals(3, pizzeria.getOrderQueue().getOrdersCount());
        assertEquals(3, pizzeria.getWarehouse().getOrdersCount());
    }
}