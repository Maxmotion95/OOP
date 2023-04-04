package ru.nsu.fit.lylova.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;
import ru.nsu.fit.lylova.staff.bakers.Baker;
import ru.nsu.fit.lylova.staff.bakers.NormalBaker;

/**
 * Baker service class.
 */
public class BakerService {
    private final List<Baker> bakers;

    /**
     * Create baker service with specified parameters.
     *
     * @param orderQueue pizzeria order queue
     * @param warehouse  pizzeria warehouse
     * @param logger     logger
     * @param configPath path to bakers configuration file
     * @throws IOException if configuration file is missing
     */
    public BakerService(PizzeriaOrderQueue orderQueue, PizzaWarehouse warehouse, Logger logger, String configPath) throws IOException {
        bakers = new ArrayList<>();
        Path filePath = Path.of(configPath);
        String configString = Files.readString(filePath);
        JsonParser parser = new JsonParser();
        JsonElement configNode = parser.parse(configString);
        JsonArray bakersArray = configNode.getAsJsonObject().get("bakers").getAsJsonArray();
        for (int i = 0; i < bakersArray.size(); ++i) {
            JsonObject courierJsonObject = bakersArray.get(i).getAsJsonObject();
            String type = courierJsonObject.get("type").getAsString();
            Baker baker = null;
            switch (type) {
                case "norm":
                    baker = new NormalBaker(
                            orderQueue,
                            warehouse,
                            courierJsonObject.get("name").getAsString(),
                            logger,
                            courierJsonObject.get("cookingSpeed").getAsLong()
                    );
                    break;
                default:
                    break;
            }
            if (baker != null) {
                bakers.add(baker);
            }
        }
    }

    /**
     * Start the work of all bakers.
     */
    public void startAll() {
        bakers.forEach(Thread::start);
    }

    /**
     * Shuts down all bakers so that they finish all orders.
     */
    public void softShutdownAll() {
        bakers.forEach(Baker::softShutdown);
    }

    /**
     * Shuts down all bakers urgently so that they don't finish all orders.
     */
    public void forceShutdownAll() {
        bakers.forEach(Baker::forceShutdown);
    }

    /**
     * Waits for all bakers to complete their work.
     *
     * @throws InterruptedException if thread was interrupted
     */
    public void joinAll() throws InterruptedException {
        for (Baker baker : bakers) {
            baker.join();
        }
    }
}
