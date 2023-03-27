package ru.nsu.fit.lylova.staff.couriers;

import ru.nsu.fit.lylova.PizzaOrder;
import ru.nsu.fit.lylova.PizzaWarehouse;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public abstract class Courier extends Thread {
    protected final PizzaWarehouse warehouse;
    protected final String name;
    protected final Logger logger;
    protected boolean isSoftShutdown = false;
    protected boolean isForceShutdown = false;

    protected Courier(PizzaWarehouse warehouse, String name, Logger logger) {
        this.warehouse = warehouse;
        this.name = name;
        this.logger = logger;
    }

    public void softShutdown() {
        isSoftShutdown = true;
        this.interrupt();
    }

    public void forceShutdown() {
        isForceShutdown = true;
        this.interrupt();
    }


    public void run() {
        this.startWork();
        Queue<PizzaOrder> trunk = new LinkedList<>();
        while (!isForceShutdown) {
            boolean isTrunkFilled = false;
            while (!isTrunkFilled) {
                try {
                    this.fillTrunk(trunk);
                    isTrunkFilled = true;
                } catch (InterruptedException e) {
                    if (isForceShutdown) {
                        break;
                    }
                }
            }
            if (isForceShutdown) {
                break;
            }
            if (isSoftShutdown && trunk.isEmpty()) {
                break;
            }

            this.deliverOrdersFromTrunk(trunk);
        }
        this.endWork();
    }

    protected abstract void startWork();

    protected abstract void fillTrunk(Queue<PizzaOrder> trunk) throws InterruptedException;

    protected abstract void deliverOrdersFromTrunk(Queue<PizzaOrder> trunk);

    protected abstract void endWork();
}
