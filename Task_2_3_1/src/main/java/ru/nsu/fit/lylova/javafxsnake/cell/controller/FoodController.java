package ru.nsu.fit.lylova.javafxsnake.cell.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Pair;
import ru.nsu.fit.lylova.javafxsnake.cell.CellFactory;
import ru.nsu.fit.lylova.javafxsnake.cell.CellType;

import java.io.IOException;

public class FoodController extends CellController {
    @Override
    protected void applyConfig() {
        if ((x + y) % 2 == 0) {
            setBackgroundColor((String) config.get("field_color_1"));
        } else {
            setBackgroundColor((String) config.get("field_color_2"));
        }
    }

    @Override
    public Pair<Node, CellController> changeCellType(CellType type) {
        if (type == CellType.FOOD) {
            return null;
        }
        FXMLLoader result;
        result = CellFactory.createCell(type);
        Node node;
        try {
            node = result.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CellController controller = result.getController();
        controller.setConfig(config, x, y);
        return new Pair<>(node, controller);
    }
}
