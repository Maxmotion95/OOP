package ru.nsu.fit.lylova.javafxsnake;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.nsu.fit.lylova.model.Direction;

import java.io.IOException;

public class StartScreenController {

    private SnakeFieldController fieldController;

    public void switchToGameField(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SnakeApplication.class.getResource("snake_field.fxml"));
        Parent root = fxmlLoader.load();
        fieldController = fxmlLoader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new KeyHandler());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToSettingsScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SnakeApplication.class.getResource("settings_screen.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new KeyHandler());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private class KeyHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case UP, W -> fieldController.setSnakeDirection(Direction.UP);
                case RIGHT, D -> fieldController.setSnakeDirection(Direction.RIGHT);
                case DOWN, S -> fieldController.setSnakeDirection(Direction.DOWN);
                case LEFT, A -> fieldController.setSnakeDirection(Direction.LEFT);
                case ENTER, SPACE -> fieldController.startGame();
                case ESCAPE -> fieldController.stopGame();
            }
        }
    }
}
