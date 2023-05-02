package ru.nsu.fit.lylova.javafxsnake.cellControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import ru.nsu.fit.lylova.javafxsnake.CellFactory;
import ru.nsu.fit.lylova.javafxsnake.CellType;

import java.io.IOException;

import static ru.nsu.fit.lylova.javafxsnake.CellType.STRAIGHT_BODY;

public class StraightBodyPieceController extends CellController {
    @FXML
    private Rectangle body;

    @Override
    protected void applyConfig() {
        String bodyColor = (String) config.get("body_color");
        body.setFill(Paint.valueOf(bodyColor));

        if ((x + y) % 2 == 0) {
            setBackgroundColor((String) config.get("field_color_1"));
        } else {
            setBackgroundColor((String) config.get("field_color_2"));
        }
    }

    @Override
    public Pair<Node, CellController> changeCellType(CellType type) {
        if (type == STRAIGHT_BODY) {
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
