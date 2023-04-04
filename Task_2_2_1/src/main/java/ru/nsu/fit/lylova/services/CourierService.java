package ru.nsu.fit.lylova.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.staff.couriers.ActiveCourier;
import ru.nsu.fit.lylova.staff.couriers.Courier;
import ru.nsu.fit.lylova.staff.couriers.LazyCourier;

/**
 * Courier service class.
 */
public class CourierService {
    private final List<Courier> couriers;

    /**
     * Create courier service with specified parameters.
     *
     * @param warehouse  pizzeria warehouse
     * @param logger     logger
     * @param configPath path to couriers configuration file
     * @throws IOException if configuration file is missing
     */
    public CourierService(PizzaWarehouse warehouse,
                          Logger logger,
                          String configPath) throws IOException {
        couriers = new ArrayList<>();
        Path filePath = Path.of(configPath);
        String configString = Files.readString(filePath);
        JsonParser parser = new JsonParser();
        JsonElement configNode = parser.parse(configString);
        JsonArray couriersArray = configNode.getAsJsonObject().get("couriers").getAsJsonArray();
        for (int i = 0; i < couriersArray.size(); ++i) {
            JsonObject courierJsonObject = couriersArray.get(i).getAsJsonObject();
            String type = courierJsonObject.get("type").getAsString();
            Courier courier = null;
            switch (type) {
                case "lazy":
                    courier = new LazyCourier(
                            warehouse,
                            courierJsonObject.get("name").getAsString(),
                            logger,
                            courierJsonObject.get("deliveryTime").getAsLong(),
                            courierJsonObject.get("trunkSize").getAsInt()
                    );
                    break;
                case "active":
                    courier = new ActiveCourier(
                            warehouse,
                            courierJsonObject.get("name").getAsString(),
                            logger,
                            courierJsonObject.get("deliveryTime").getAsLong(),
                            courierJsonObject.get("trunkSize").getAsInt()
                    );
                    break;
                default:
                    break;
            }
            if (courier != null) {
                couriers.add(courier);
            }
        }
    }

    /**
     * Start the work of all couriers.
     */
    public void startAll() {
        couriers.forEach(Thread::start);
    }

    /**
     * Shuts down all couriers so that they finish all orders.
     */
    public void softShutdownAll() {
        couriers.forEach(Courier::softShutdown);
    }

    /**
     * Shuts down all couriers urgently so that they don't finish all orders.
     */
    public void forceShutdownAll() {
        couriers.forEach(Courier::forceShutdown);
    }

    /**
     * Waits for all couriers to complete their work.
     *
     * @throws InterruptedException if thread was interrupted
     */
    public void joinAll() throws InterruptedException {
        for (Courier courier : couriers) {
            courier.join();
        }
    }
}
