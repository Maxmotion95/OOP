package ru.nsu.fit.lylova.javafxsnake.cellControllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import ru.nsu.fit.lylova.javafxsnake.CellType;

import java.util.Map;

public abstract class CellController {
    protected int x;
    protected int y;
    protected Map<String, Object> config;
    @FXML
    Pane background;

    public void setConfig(Map<String, Object> config, int x, int y) {
        this.config = config;
        this.x = x;
        this.y = y;
        resizeCell();
        applyConfig();
    }

    protected void setBackgroundColor(String color) {
        background.setStyle("-fx-background-color: " + color);
    }

    protected void resizeCell() {
        double currentSize = background.getPrefHeight();
        double cellSize = (Double) config.get("cell_size");
        background.setScaleX(cellSize / currentSize);
        background.setScaleY(cellSize / currentSize);
    }

    protected abstract void applyConfig();

    abstract public Pair<Node, CellController> changeCellType(CellType type);
//    {
//        if (type == cellType) {
//            return null;
//        }
//        FXMLLoader result;
//        result = CellFactory.createCell(type);
//        try {
//            result.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        CellController controller = result.getController();
//        controller.changeSnakeColor(bodyColor, eyeColor);
//        controller.changeBackgroundColor(backgroundColor);
//        return result;
//    }
}
