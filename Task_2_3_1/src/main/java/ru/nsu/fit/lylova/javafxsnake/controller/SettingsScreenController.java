package ru.nsu.fit.lylova.javafxsnake.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;
import ru.nsu.fit.lylova.javafxsnake.SnakeApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {
    @FXML
    private Label fieldWidthLabel;
    @FXML
    private Label fieldHeightLabel;
    @FXML
    private Label foodCountLabel;
    @FXML
    private Label stonesCountLabel;
    @FXML
    private ColorPicker snakeBodyColorPicker;
    @FXML
    private ColorPicker snakeEyesColorPicker;
    @FXML
    private ColorPicker stoneColorPicker;

    @FXML
    private ToggleGroup speedGroup;
    private Map<String, Object> config;
    private Map<String, Object> past_config;
    private int maxFieldWidth = 20;
    private int minFieldWidth = 5;
    private int maxFieldHeight = 20;
    private int minFieldHeight = 5;
    private int maxFoodCount = 5;
    private int maxStonesCount = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File(Objects.requireNonNull(
                SnakeApplication.class.getResource("current_game_config.yml")).getFile());
        try {
            InputStream inputStream = new FileInputStream(file);
            Yaml yaml = new Yaml();
            config = yaml.load(inputStream);
            past_config = new HashMap<>(config);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        fieldWidthLabel.setText(Integer.toString((Integer) config.get("width")));
        fieldHeightLabel.setText(Integer.toString((Integer) config.get("height")));
        foodCountLabel.setText(Integer.toString((Integer) config.get("food_count")));
        stonesCountLabel.setText(Integer.toString((Integer) config.get("rocks_count")));

        snakeBodyColorPicker.setValue(Color.web((String) config.get("body_color")));
        snakeEyesColorPicker.setValue(Color.web((String) config.get("eye_color")));
        stoneColorPicker.setValue(Color.web((String) config.get("rock_color")));

        for (var i : speedGroup.getToggles()) {
            RadioButton button = (RadioButton) i;
            button.setSelected(button.getText().toLowerCase().equals(config.get("speed")));
        }
    }

    public void saveSettings() throws IOException {
        //checking that has differences
        boolean isDiffInConfigs = false;
        for (var i : past_config.keySet()) {
            if (!i.contains("color")) {
                isDiffInConfigs |= !past_config.get(i).equals(config.get(i));
            }
        }
        if (isDiffInConfigs) {
            config.put("record_score", 0);
        }

        Yaml yaml = new Yaml();
        String configString = yaml.dump(config);

        FileWriter fileWriter = new FileWriter(Objects.requireNonNull(
                SnakeApplication.class.getResource("current_game_config.yml")).getFile());
        fileWriter.write(configString);
        fileWriter.close();
    }

    public void increaseFieldWidth() {
        int currentWidth = (Integer) config.get("width");
        if (currentWidth < maxFieldWidth) {
            config.put("width", currentWidth + 1);
        }
        currentWidth = (Integer) config.get("width");
        fieldWidthLabel.setText(Integer.toString(currentWidth));
    }

    public void decreaseFieldWidth() {
        int currentWidth = (Integer) config.get("width");
        if (currentWidth > minFieldWidth) {
            config.put("width", currentWidth - 1);
        }
        currentWidth = (Integer) config.get("width");
        fieldWidthLabel.setText(Integer.toString(currentWidth));
    }

    public void increaseFieldHeight() {
        int currentHeight = (Integer) config.get("height");
        if (currentHeight < maxFieldHeight) {
            config.put("height", currentHeight + 1);
        }
        currentHeight = (Integer) config.get("height");
        fieldHeightLabel.setText(Integer.toString(currentHeight));
    }

    public void decreaseFieldHeight() {
        int currentHeight = (Integer) config.get("height");
        if (currentHeight > minFieldHeight) {
            config.put("height", currentHeight - 1);
        }
        currentHeight = (Integer) config.get("height");
        fieldHeightLabel.setText(Integer.toString(currentHeight));
    }

    public void increaseFoodCount() {
        int currentFoodCount = (Integer) config.get("food_count");
        if (currentFoodCount < maxFoodCount) {
            config.put("food_count", currentFoodCount + 1);
        }
        currentFoodCount = (Integer) config.get("food_count");
        foodCountLabel.setText(Integer.toString(currentFoodCount));
    }

    public void decreaseFoodCount() {
        int currentFoodCount = (Integer) config.get("food_count");
        if (currentFoodCount > 1) {
            config.put("food_count", currentFoodCount - 1);
        }
        currentFoodCount = (Integer) config.get("food_count");
        foodCountLabel.setText(Integer.toString(currentFoodCount));
    }

    public void increaseStonesCount() {
        int currentStonesCount = (Integer) config.get("rocks_count");
        if (currentStonesCount < maxStonesCount) {
            config.put("rocks_count", currentStonesCount + 1);
        }
        currentStonesCount = (Integer) config.get("rocks_count");
        stonesCountLabel.setText(Integer.toString(currentStonesCount));
    }

    public void decreaseStonesCount() {
        int currentStonesCount = (Integer) config.get("rocks_count");
        if (currentStonesCount > 0) {
            config.put("rocks_count", currentStonesCount - 1);
        }
        currentStonesCount = (Integer) config.get("rocks_count");
        stonesCountLabel.setText(Integer.toString(currentStonesCount));
    }

    public void updateSpeed() {
        String newSpeed = ((RadioButton) speedGroup.getSelectedToggle()).getText().toLowerCase();
        config.put("speed", newSpeed);
    }

    public void updateBodyColor() {
        Color bodyColor = snakeBodyColorPicker.getValue();
        config.put("body_color", bodyColor.toString());
    }

    public void updateEyeColor() {
        Color eyeColor = snakeEyesColorPicker.getValue();
        config.put("eye_color", eyeColor.toString());
    }

    public void updateStoneColor() {
        Color stoneColor = stoneColorPicker.getValue();
        config.put("rock_color", "rgba(" + Math.round(255 * stoneColor.getRed()) + ","
                + Math.round(255 * stoneColor.getGreen()) + ","
                + Math.round(255 * stoneColor.getBlue()) + ","
                + stoneColor.getOpacity() + ")");
    }

    public void switchToStartScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SnakeApplication.class.getResource("screens/start_screen.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}