package ru.nsu.fit.lylova.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.nsu.fit.lylova.environment.PizzaWarehouse;
import ru.nsu.fit.lylova.environment.PizzeriaOrderQueue;
import ru.nsu.fit.lylova.staff.bakers.Baker;
import ru.nsu.fit.lylova.staff.bakers.NormalBaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BakerService {
    private final List<Baker> bakers;

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

    public void startAll() {
        bakers.forEach(Thread::start);
    }

    public void softShutdownAll() {
        bakers.forEach(Baker::softShutdown);
    }

    public void forceShutdownAll() {
        bakers.forEach(Baker::forceShutdown);
    }

    public void joinAll() throws InterruptedException {
        for (Baker baker : bakers) {
            baker.join();
        }
    }
}
