package ru.nsu.fit.lylova.javafxsnake.cell;

import javafx.fxml.FXMLLoader;
import ru.nsu.fit.lylova.javafxsnake.SnakeApplication;

public class CellFactory {
    static public FXMLLoader createCell(CellType cellType) {
        String cellFileName = "";
        switch (cellType) {
            case TALE:
                cellFileName = "cells/tale.fxml";
                break;
            case HEAD:
                cellFileName = "cells/head.fxml";
                break;
            case EMPTY:
                cellFileName = "cells/empty_cell.fxml";
                break;
            case ROCK:
                cellFileName = "cells/rock_cell.fxml";
                break;
            case FOOD:
                cellFileName = "cells/food_apple.fxml";
                break;
            case ANGULAR_BODY:
                cellFileName = "cells/angular_body_piece.fxml";
                break;
            case STRAIGHT_BODY:
                cellFileName = "cells/straight_body_piece.fxml";
        }
        return new FXMLLoader(SnakeApplication.class.getResource(cellFileName));
    }
}
