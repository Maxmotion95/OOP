package ru.nsu.fit.lylova.javafxsnake.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {
    private final ToggleGroup presetsGroup = new ToggleGroup();
    @FXML
    private Label fieldWidthLabel;
    @FXML
    private Label fieldHeightLabel;
    @FXML
    private Label foodCountLabel;
    @FXML
    private Label stonesCountLabel;
    @FXML
    private GridPane presetsGridPane;
    @FXML
    private ToggleGroup speedGroup;
    @FXML
    private Label minWidthLabel;
    @FXML
    private Label maxWidthLabel;
    @FXML
    private Label minHeightLabel;
    @FXML
    private Label maxHeightLabel;
    @FXML
    private Label minFoodCountLabel;
    @FXML
    private Label maxFoodCountLabel;
    @FXML
    private Label minStonesCountLabel;
    @FXML
    private Label maxStonesCountLabel;
    private Map<String, Object> config;
    private Map<String, Object> past_config;
    private final int maxFieldWidth = 20;
    private final int minFieldWidth = 5;
    private final int maxFieldHeight = 20;
    private final int minFieldHeight = 5;
    private final int maxFoodCount = 5;
    private final int maxStonesCount = 10;

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
        minWidthLabel.setText("min: " + minFieldWidth);
        maxWidthLabel.setText("max: " + maxFieldWidth);

        fieldHeightLabel.setText(Integer.toString((Integer) config.get("height")));
        minHeightLabel.setText("min: " + minFieldHeight);
        maxHeightLabel.setText("max: " + maxFieldHeight);

        foodCountLabel.setText(Integer.toString((Integer) config.get("food_count")));
        minFoodCountLabel.setText("min: " + 1);
        maxFoodCountLabel.setText("max: " + maxFoodCount);

        stonesCountLabel.setText(Integer.toString((Integer) config.get("rocks_count")));
        minStonesCountLabel.setText("min: " + 0);
        maxStonesCountLabel.setText("max: " + maxStonesCount);


        for (var i : speedGroup.getToggles()) {
            RadioButton button = (RadioButton) i;
            button.setSelected(button.getText().toLowerCase().equals(config.get("speed")));
        }

        int presetsCount = ((List) config.get("presets")).size();
        int currentPresetId = (Integer) config.get("preset_id");
        for (int preset_id = 0; preset_id < presetsCount; ++preset_id) {
            FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("snake_preset_button.fxml"));
            Node node;
            try {
                node = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            presetsGridPane.add(node, preset_id, 0);
            SnakePresetButtonController controller = fxmlLoader.getController();
            controller.setSettingsScreenController(this);
            controller.setPresetId(preset_id);
            controller.setRadioButtonToggleGroup(presetsGroup);
            controller.setSelectedRadioButton(preset_id == currentPresetId);
            GridPane.setValignment(node, VPos.CENTER);
            GridPane.setHalignment(node, HPos.CENTER);
        }
    }

    public void saveSettings() {
        //checking that has differences
        boolean isDiffInConfigs = false;
        for (var i : past_config.keySet()) {
            if (!i.contains("preset")) {
                isDiffInConfigs |= !past_config.get(i).equals(config.get(i));
            }
        }
        if (isDiffInConfigs) {
            config.put("record_score", 0);
        }

        Yaml yaml = new Yaml();
        String configString = yaml.dump(config);

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(Objects.requireNonNull(
                    SnakeApplication.class.getResource("current_game_config.yml")).getFile());
            fileWriter.write(configString);
            fileWriter.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saving results");
            alert.setHeaderText("OOPS, there are some errors");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saving results");
        alert.setHeaderText("Settings saved");
        alert.showAndWait();
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

    public void updatePresetId(int presetId) {
        config.put("preset_id", presetId);
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