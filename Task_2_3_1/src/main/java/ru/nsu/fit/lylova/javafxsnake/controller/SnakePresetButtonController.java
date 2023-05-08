package ru.nsu.fit.lylova.javafxsnake.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.yaml.snakeyaml.Yaml;
import ru.nsu.fit.lylova.javafxsnake.SnakeApplication;
import ru.nsu.fit.lylova.javafxsnake.cell.CellType;
import ru.nsu.fit.lylova.javafxsnake.cell.controller.CellController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import static ru.nsu.fit.lylova.javafxsnake.cell.CellFactory.createCell;

public class SnakePresetButtonController implements Initializable {
    @FXML
    private Pane buttonBackgroundPane;
    @FXML
    private GridPane field;
    @FXML
    private RadioButton radioButton;

    private SettingsScreenController settingsScreenController;
    private final CellController[][] cellControllers = new CellController[2][3];
    private Map<String, Object> config;

    private int presetId;
    private final CellType[][] cellTypes = {
            {CellType.HEAD, null, CellType.TALE},
            {CellType.ANGULAR_BODY, CellType.STRAIGHT_BODY, CellType.ANGULAR_BODY}
    };
    private final double[][] rotateAngles = {
            {180, 0, 0},
            {180, 90, 270}
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        {
            File file = new File(Objects.requireNonNull(
                    SnakeApplication.class.getResource("current_game_config.yml")).getFile());
            try {
                InputStream inputStream = new FileInputStream(file);
                Yaml yaml = new Yaml();
                config = yaml.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }



        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (cellTypes[i][j] == null) {
                    continue;
                }
                FXMLLoader fxmlLoader = createCell(cellTypes[i][j]);
                Node node;
                try {
                    node = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                node.setRotate(rotateAngles[i][j]);
                field.add(node, i, j);
                cellControllers[i][j] = fxmlLoader.getController();
            }
        }

    }

    public void setSettingsScreenController(SettingsScreenController settingsScreenController) {
        this.settingsScreenController = settingsScreenController;
    }

    public void setPresetId(int presetId) {
        this.presetId = presetId;
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (cellTypes[i][j] == null) {
                    String color;
                    if ((i + j) % 2 == 0) {
                        color = (String) config.get("field_color_1");
                    } else {
                        color = (String) config.get("field_color_2");
                    }
                    buttonBackgroundPane.setStyle("-fx-background-color: " + color);
                    continue;
                }
                Map<String, Object> cellConfig = new HashMap<>(config);
                cellConfig.put("preset_id", presetId);
                cellConfig.put("cell_size", 60.0);
                cellControllers[i][j].setConfig(cellConfig, i, j);
            }
        }
    }

    public void updatePresetId() {
        settingsScreenController.updatePresetId(presetId);
    }

    public void setRadioButtonToggleGroup(ToggleGroup toggleGroup) {
        radioButton.setToggleGroup(toggleGroup);
    }

    public void setSelectedRadioButton(Boolean value) {
        radioButton.setSelected(value);
    }
}
