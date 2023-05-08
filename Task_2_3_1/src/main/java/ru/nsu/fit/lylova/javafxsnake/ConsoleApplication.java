package ru.nsu.fit.lylova.javafxsnake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;
import ru.nsu.fit.lylova.console.GameController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ConsoleApplication extends Application {
    public static void main(String[] args) {
        Map<String, Object> config = new HashMap<>();
        config.put("height", 10);
        config.put("width", 10);
        config.put("food_count", 2);
        config.put("rock_count", 2);
        config.put("speed", 750);
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-h")) {
                int height;
                try {
                    height = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("height must be integer");
                    return;
                }
                if (height <= 20 && height >= 5) {
                    config.put("height", height);
                } else {
                    System.out.println("height must be in interval [5, 20]");
                    return;
                }
                ++i;
                continue;
            }
            if (args[i].equals("-w")) {
                int width;
                try {
                    width = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("width must be integer");
                    return;
                }
                if (width <= 20 && width >= 5) {
                    config.put("width", width);
                } else {
                    System.out.println("width must be in interval [5, 20]");
                    return;
                }
                ++i;
                continue;
            }
            if (args[i].equals("-f")) {
                int foodCount;
                try {
                    foodCount = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("food count must be integer");
                    return;
                }
                if (foodCount <= 5 && foodCount >= 1) {
                    config.put("food_count", foodCount);
                } else {
                    System.out.println("food count must be in interval [1, 5]");
                    return;
                }
                ++i;
                continue;
            }
            if (args[i].equals("-r")) {
                int rockCount;
                try {
                    rockCount = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("rock count must be integer");
                    return;
                }
                if (rockCount <= 5 && rockCount >= 0) {
                    config.put("rock_count", rockCount);
                } else {
                    System.out.println("rock count must be in interval [0, 5]");
                    return;
                }
                ++i;
                continue;
            }
            if (args[i].equals("-s")) {
                if (args[i + 1].equals("slow")) {
                    config.put("speed", 1000);
                } else if (args[i + 1].equals("medium")) {
                    config.put("speed", 750);
                } else if (args[i + 1].equals("fast")) {
                    config.put("speed", 500);
                } else {
                    System.out.println("speed must be [\"slow\" | \"medium\" | \"fast\"]");
                    return;
                }
                ++i;
                continue;
            }
            System.out.println("Unknown option");
        }

        Yaml yaml = new Yaml();
        String configString = yaml.dump(config);

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(Objects.requireNonNull(
                    GameController.class.getResource("game_config.yml")).getFile());
            fileWriter.write(configString);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("OOPS, some errors occurred while saving config");
        }
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1, 1);
        GameController gameController = new GameController(
                stage
        );
        scene.setOnKeyPressed(gameController::handleKeyEvent);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
