package ru.nsu.fit.lylova.console;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;
import ru.nsu.fit.lylova.model.ConnectedCellSides;
import ru.nsu.fit.lylova.model.Direction;
import ru.nsu.fit.lylova.model.Game;

import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private Game game = null;
    private Timer timer = null;
    private int fieldWidth;
    private int fieldHeight;
    private boolean wasGameStarted = false;
    private boolean inPause = false;
    private long speed = 1000;
    private final Stage stage;

    public GameController(Stage stage) {
        this.stage = stage;
        Map<String, Object> gameConfig;
        {
            File file = new File(Objects.requireNonNull(
                    GameController.class.getResource("game_config.yml")).getFile());
            try {
                InputStream inputStream = new FileInputStream(file);
                Yaml yaml = new Yaml();
                gameConfig = yaml.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        this.fieldHeight = (Integer) gameConfig.get("height");
        this.fieldWidth = (Integer) gameConfig.get("width");
        int rockCount = (Integer) gameConfig.get("rock_count");
        int foodCount = (Integer) gameConfig.get("food_count");
        speed = (Integer) gameConfig.get("speed");
        game = new Game(
                fieldWidth, fieldHeight,
                (fieldWidth - 1) / 2, (fieldHeight - 1) / 2,
                rockCount, foodCount
        );
        printField();
    }

    private void printField() {
        for (int j = -1;  j < fieldHeight + 1; ++j) {
            for (int i = -1; i < fieldWidth + 1; ++i) {
                if (i == -1 || j == -1 || j >= fieldHeight || i >= fieldWidth) {
                    System.out.print("#");
                    continue;
                }
                switch (game.getCellType(i, j)) {
                    case FOOD:
                        System.out.print("o");
                        break;
                    case ROCK:
                        System.out.print("#");
                        break;
                    case EMPTY:
                        System.out.print(" ");
                        break;
                    case TALE:
                        switch (game.getConnectedCellSides(i, j)) {
                            case DOWN:
                            case UP:
                                System.out.print("|");
                                break;
                            default:
                                System.out.print("-");
                        }
                        break;
                    case HEAD:
                        System.out.print("*");
                        break;
                    case STRAIGHT_BODY:
                        if (game.getConnectedCellSides(i, j) == ConnectedCellSides.UP_DOWN) {
                            System.out.print("|");
                        } else {
                            System.out.print("-");
                        }
                        break;
                    case ANGULAR_BODY:
                        System.out.print("+");
                }
            }
            if (j == (fieldHeight - 1) / 2) {
                System.out.print("  Score: " + game.getScore());
            }

            System.out.print("\n");
        }
        System.out.flush();
    }

    private void proceedGameTurn() {
        game.proceedGame();

        if (game.getIsEndOfGame()) {
            timer.cancel();
            System.out.print("GAME OVER!!!!\nYOUR SCORE: " + game.getScore());
            Platform.runLater(stage::close);
            return;
        }

        printField();
    }

    private void startGame() {
        if (!inPause && !wasGameStarted) {
            timer = new Timer();
            timer.schedule(new GameTimerTask(), speed, speed);
            wasGameStarted = true;
        }
    }

    private void exitFromPause() {
        if (inPause) {
            inPause = false;
            if (wasGameStarted) {
                timer = new Timer();
                timer.schedule(new GameTimerTask(), speed, speed);
            }
        }
    }

    private void stopGame() {
        if (!inPause && !game.getIsEndOfGame()) {
            if (timer != null) {
                timer.cancel();
            }
            inPause = true;
        }
    }

    private void setSnakeDirection(Direction direction) {
        if (!inPause && !game.getIsEndOfGame() && wasGameStarted) {
            game.setSnakeDirection(direction);
        }
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
            case W:
                setSnakeDirection(Direction.UP);
                break;
            case RIGHT:
            case D:
                setSnakeDirection(Direction.RIGHT);
                break;
            case DOWN:
            case S:
                setSnakeDirection(Direction.DOWN);
                break;
            case LEFT:
            case A:
                setSnakeDirection(Direction.LEFT);
                break;
            case ENTER:
            case SPACE:
                if (inPause) {
                    exitFromPause();
                } else {
                    startGame();
                }
                break;
            case ESCAPE:
                stopGame();
        }
    }

    class GameTimerTask extends TimerTask {
        @Override
        public void run() {
            proceedGameTurn();
        }
    }
}
