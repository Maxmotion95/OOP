package ru.nsu.fit.lylova.javafxsnake.cellControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import ru.nsu.fit.lylova.javafxsnake.CellFactory;
import ru.nsu.fit.lylova.javafxsnake.CellType;

import java.io.IOException;

public class HeadController extends CellController {
    @FXML
    Rectangle body_rect1;
    @FXML
    Circle body_circ1;
    @FXML
    Circle body_circ2;
    @FXML
    Circle body_circ3;
    @FXML
    Circle eye_circ1;
    @FXML
    Circle eye_circ2;
    @FXML
    Circle nose_circ1;
    @FXML
    Circle nose_circ2;

    @Override
    protected void applyConfig() {
        String bodyColor = (String) config.get("body_color");
        body_rect1.setFill(Paint.valueOf(bodyColor));
        body_circ1.setFill(Paint.valueOf(bodyColor));
        body_circ2.setFill(Paint.valueOf(bodyColor));
        body_circ3.setFill(Paint.valueOf(bodyColor));

        String eyeColor = (String) config.get("eye_color");
        eye_circ1.setFill(Paint.valueOf(eyeColor));
        eye_circ2.setFill(Paint.valueOf(eyeColor));
        nose_circ1.setFill(Paint.valueOf(eyeColor));
        nose_circ2.setFill(Paint.valueOf(eyeColor));

        if ((x + y) % 2 == 0) {
            setBackgroundColor((String) config.get("field_color_1"));
        } else {
            setBackgroundColor((String) config.get("field_color_2"));
        }
    }

    @Override
    public Pair<Node, CellController> changeCellType(CellType type) {
        if (type == CellType.HEAD) {
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
