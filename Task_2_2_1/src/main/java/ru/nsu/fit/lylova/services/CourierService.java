package ru.nsu.fit.lylova.services;

import com.google.gson.*;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.staff.couriers.ActiveCourier;
import ru.nsu.fit.lylova.staff.couriers.Courier;
import ru.nsu.fit.lylova.staff.couriers.LazyCourier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CourierService {
    private final List<Courier> couriers;

    public CourierService(PizzaWarehouse warehouse, Logger logger, String configPath) throws IOException {
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

    public void startAll() {
        couriers.forEach(Thread::start);
    }

    public void softShutdownAll() {
        couriers.forEach(Courier::softShutdown);
    }

    public void forceShutdownAll() {
        couriers.forEach(Courier::forceShutdown);
    }

    public void joinAll() throws InterruptedException {
        for (Courier courier : couriers) {
            courier.join();
        }
    }
}
