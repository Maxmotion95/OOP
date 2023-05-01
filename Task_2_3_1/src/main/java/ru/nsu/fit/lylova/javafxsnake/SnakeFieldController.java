package ru.nsu.fit.lylova.javafxsnake;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;
import ru.nsu.fit.lylova.javafxsnake.cellControllers.CellController;
import ru.nsu.fit.lylova.model.Direction;
import ru.nsu.fit.lylova.model.Game;
import ru.nsu.fit.lylova.model.Point;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SnakeFieldController implements Initializable {
    @FXML
    private GridPane field;

    @FXML
    private Label scoreLabel;

    @FXML
    private Pane pausePane;

    @FXML
    Pane gameOverPane;

    @FXML
    Label gameOverScoreLabel;

    private CellController[][] controllers;
    private Node[][] cells;
    private Game game;
    private Timer timer = null;
    private int fieldWidth;
    private int fieldHeight;
    private boolean wasGameStarted = false;
    private boolean inPause = false;
    private Map<String, Object> config;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File(Objects.requireNonNull(getClass().getResource("config.yml")).getFile());
//        Map<String, Object> data;
        try {
            InputStream inputStream = new FileInputStream(file);
            Yaml yaml = new Yaml();
            config = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        field.getRowConstraints().clear();
        field.getColumnConstraints().clear();
        field.getChildren().clear();

        fieldHeight = (Integer) config.get("height");
        fieldWidth = (Integer) config.get("width");
        this.game = new Game(fieldWidth, fieldHeight, 3, 3, (Integer) config.get("rocks_count"), (Integer) config.get("food_count"));

        double cellSize = 50;
        config.put("cell_size", cellSize);

        for (int i = 0; i < fieldHeight; ++i) {
            field.getRowConstraints().add(new RowConstraints(cellSize));
        }
        for (int i = 0; i < fieldWidth; ++i) {
            field.getColumnConstraints().add(new ColumnConstraints(cellSize));
        }
        controllers = new CellController[fieldWidth][fieldHeight];
        cells = new Node[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; ++i) {
            for (int j = 0; j < fieldHeight; ++j) {
                FXMLLoader fxmlLoader = CellFactory.createCell(game.getCellType(i, j));
                try {
                    cells[i][j] = fxmlLoader.load();
                    controllers[i][j] = fxmlLoader.getController();

//                    double currentSize = ((Pane) cells[i][j]).getPrefHeight();
//                    cells[i][j].setScaleX(cellSize / currentSize);
//                    cells[i][j].setScaleY((double) 5);
                    cells[i][j].setRotate(getRotateAngleOfCell(i, j));
                    //((Pane)cells[i][j]).getPrefHeight()
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                controllers[i][j].setConfig(config, i, j);
                field.add(cells[i][j], i, j);
            }
        }
        updateScoreLabel();
    }

    public void restartGame() {
        gameOverPane.setVisible(false);
        inPause = false;
        wasGameStarted = false;
        this.game = new Game(fieldWidth, fieldHeight, 3, 3, (Integer) config.get("rocks_count"), (Integer) config.get("food_count"));
        for (int i = 0; i < fieldWidth; ++i) {
            for (int j = 0; j < fieldHeight; ++j) {
                var nodeCellControllerPair = controllers[i][j].changeCellType(game.getCellType(i, j));
                if (nodeCellControllerPair != null) {
                    field.getChildren().remove(cells[i][j]);
                    cells[i][j] = nodeCellControllerPair.getKey();
                    controllers[i][j] = nodeCellControllerPair.getValue();

                    field.add(cells[i][j], i, j);
                }

                cells[i][j].setRotate(getRotateAngleOfCell(i, j));
            }
        }
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreLabel.setText(Integer.toString(game.getScore()));
    }

    private int getRotateAngleOfCell(int x, int y) {
        CellType type = game.getCellType(x, y);
        if (type != CellType.ANGULAR_BODY && type != CellType.HEAD &&
                type != CellType.TALE && type != CellType.STRAIGHT_BODY) {
            return 0;
        }

        Point prevPoint = game.getPrevSnakePoint(x, y);
        Point nextPoint = game.getNextSnakePoint(x, y);
        if (type == CellType.TALE) {
            return (360 - getVectorAngle(x, y, nextPoint.getX(), nextPoint.getY())) % 360;
        }
        if (type == CellType.HEAD) {
            return (180 - getVectorAngle(x, y, prevPoint.getX(), prevPoint.getY()) + 360) % 360;
        }
        if (type == CellType.STRAIGHT_BODY) {
            if (prevPoint.getX() == nextPoint.getX()) {
                return 90;
            }
            return 0;
        }
        // type == CellType.ANGULAR_BODY
        int angleWithPrevPoint = getVectorAngle(x, y, prevPoint.getX(), prevPoint.getY());
        int angleWithNextPoint = getVectorAngle(x, y, nextPoint.getX(), nextPoint.getY());
        if (min(angleWithNextPoint, angleWithPrevPoint) == 0) {
            if (max(angleWithNextPoint, angleWithPrevPoint) == 90) {
                return 0;
            }
            return 90;
        }
        return (360 - min(angleWithNextPoint, angleWithPrevPoint)) % 360;
    }

    private int getVectorAngle(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            return 0;
        }
        if (x1 == x2) {
            if (y2 == y1 + 1) {
                return 270;
            }
            return 90;
        }
        // otherwise y1 == y2;
        if (x2 == x1 + 1) {
            return 0;
        }
        return 180;
    }

    public void startGame() {
        if (!inPause && !wasGameStarted) {
            timer = new Timer();
            timer.schedule(new GameTimerTask(), 500, 500);
            wasGameStarted = true;
        }
    }

    public void exitFromPause() {
        inPause = false;
        pausePane.setVisible(false);
        if (wasGameStarted) {
            timer = new Timer();
            timer.schedule(new GameTimerTask(), 500, 500);
        }
    }

    public void stopGame() {
        if (!inPause) {
            if (timer != null) {
                timer.cancel();
            }
            inPause = true;
            pausePane.setVisible(true);
        }
    }

    public void proceedGameTurn() {
        game.proceedGame();

        if (game.getIsEndOfGame()) {
            timer.cancel();
            displayGameOver();
            return;
        }

        for (int i = 0; i < fieldWidth; ++i) {
            for (int j = 0; j < fieldHeight; ++j) {
                var nodeCellControllerPair = controllers[i][j].changeCellType(game.getCellType(i, j));
                if (nodeCellControllerPair == null) {
                    continue;
                }
                controllers[i][j] = nodeCellControllerPair.getValue();
                field.getChildren().remove(cells[i][j]);
                cells[i][j] = nodeCellControllerPair.getKey();

                cells[i][j].setRotate(getRotateAngleOfCell(i, j));

                field.add(cells[i][j], i, j);
            }
        }
        updateScoreLabel();
    }

    private void displayGameOver() {
        gameOverPane.setVisible(true);
        gameOverScoreLabel.setText(Integer.toString(game.getScore()));
    }

    public void setSnakeDirection(Direction direction) {
        if (!inPause && !game.getIsEndOfGame() && wasGameStarted) {
            game.setSnakeDirection(direction);
        }
    }

    public void switchToStartScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("start_screen.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }






    class GameTimerTask extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(SnakeFieldController.this::proceedGameTurn);
        }
    }
}