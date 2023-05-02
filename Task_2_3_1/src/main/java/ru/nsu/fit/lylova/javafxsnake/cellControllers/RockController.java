package ru.nsu.fit.lylova.javafxsnake.cellControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Pair;
import ru.nsu.fit.lylova.javafxsnake.CellFactory;
import ru.nsu.fit.lylova.javafxsnake.CellType;

import java.io.IOException;

public class RockController extends CellController {

    @Override
    protected void applyConfig() {
        setBackgroundColor((String) config.get("rock_color"));
    }

    @Override
    public Pair<Node, CellController> changeCellType(CellType type) {
        if (type == CellType.ROCK) {
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
