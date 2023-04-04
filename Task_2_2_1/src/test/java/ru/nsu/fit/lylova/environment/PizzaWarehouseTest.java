package ru.nsu.fit.lylova.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class PizzaWarehouseTest {
    @Test
    void test() throws InterruptedException {
        PizzaWarehouse warehouse = new PizzaWarehouse(3);
        AtomicBoolean consumesFlag = new AtomicBoolean(false);
        List<ConsumerThread> consumers = new ArrayList<>(3);
        for (int i = 0; i <  3; ++i) {
            consumers.add(new ConsumerThread(warehouse, consumesFlag));
        }
        for (ConsumerThread consumer : consumers) {
            consumer.start();
        }
        consumesFlag.set(true);
        Thread.sleep(3500);
        for (var consumer : consumers) {
            consumer.interrupt();
        }
        assertEquals(3, warehouse.getOrdersCount());
    }

    public class ConsumerThread extends Thread {
        PizzaWarehouse warehouse;
        AtomicBoolean consumesFlag;

        public ConsumerThread(PizzaWarehouse warehouse, AtomicBoolean consumesFlag) {
            this.warehouse = warehouse;
            this.consumesFlag = consumesFlag;
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                if (consumesFlag.get()) {
                    try {
                        warehouse.addOrder(new PizzaOrder(1));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }
}