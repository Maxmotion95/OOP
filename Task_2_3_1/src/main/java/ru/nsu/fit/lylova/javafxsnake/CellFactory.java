package ru.nsu.fit.lylova.javafxsnake;

import javafx.fxml.FXMLLoader;

public class CellFactory {
    static public FXMLLoader createCell(CellType cellType) {
        String cellFileName = "";
        switch (cellType) {
            case TALE -> cellFileName = "cells/tale.fxml";
            case HEAD -> cellFileName = "cells/head.fxml";
            case EMPTY ->cellFileName = "cells/empty_cell.fxml";
            case ROCK -> cellFileName = "cells/rock_cell.fxml";
            case FOOD -> cellFileName = "cells/food_apple.fxml";
            case ANGULAR_BODY -> cellFileName = "cells/angular_body_piece.fxml";
            case STRAIGHT_BODY -> cellFileName = "cells/straight_body_piece.fxml";
        }
        return new FXMLLoader(SnakeApplication.class.getResource(cellFileName));
    }
}
