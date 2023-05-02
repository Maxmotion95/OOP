package ru.nsu.fit.lylova.javafxsnake.cell.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import ru.nsu.fit.lylova.javafxsnake.cell.CellFactory;
import ru.nsu.fit.lylova.javafxsnake.cell.CellType;

import java.io.IOException;

public class AngularBodyPieceController extends CellController {

    @FXML
    private Rectangle body_rect_1;
    @FXML
    private Rectangle body_rect_2;

    @Override
    protected void applyConfig() {
        body_rect_1.setFill(Paint.valueOf((String) config.get("body_color")));
        body_rect_2.setFill(Paint.valueOf((String) config.get("body_color")));

        if ((x + y) % 2 == 0) {
            setBackgroundColor((String) config.get("field_color_1"));
        } else {
            setBackgroundColor((String) config.get("field_color_2"));
        }
    }

    @Override
    public Pair<Node, CellController> changeCellType(CellType type) {
        if (type == CellType.ANGULAR_BODY) {
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
