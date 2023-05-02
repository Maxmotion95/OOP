package ru.nsu.fit.lylova.javafxsnake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SnakeApplication.class.getResource("screens/start_screen.fxml"));
        AnchorPane root = fxmlLoader.load();
//        root.setScaleX(1.5);
//        root.setScaleY(0.5);
//        root.setLayoutX(0);
//        root.setLayoutY(0);
        Scene scene = new Scene(new Pane(root), 960, 540);
       // scene.getWindow().getHeight();
//        root.setLayoutX(480/2);
//        root.setLayoutY(-270/2);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}