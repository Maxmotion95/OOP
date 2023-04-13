package ru.nsu.fit.lylova.javafxsnake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start_screen1.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
////        stage.setTitle("Hello!");
//        GridPane grid = new GridPane();
//        grid.add(new Label("Hello!"), 3, 5);
//
//        stage.setScene(scene);
//        stage.show();
//
//        Label first = new Label("First");
//        Label second = new Label("Second");
//        Label third = new Label("Third");
//
//        GridPane root = new GridPane();
//        root.getColumnConstraints().add(new ColumnConstraints(80));
//        root.getColumnConstraints().add(new ColumnConstraints(150));
//        root.getColumnConstraints().add(new ColumnConstraints(70));
//
//        root.setGridLinesVisible(true); // делаем видимой сетку строк и столбцов
//        root.setColumnIndex(first, 0);
//        root.setColumnIndex(second, 1);
//        root.setColumnIndex(third, 2);
//        root.getChildren().addAll(first, second, third);
//
//
//        scene = new Scene(root, 300, 200);
//        stage.setScene(scene);
//
//        stage.setTitle("GridPane in JavaFX");
//
//        stage.show();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("snake_field.fxml"));
//        Node root = fxmlLoader.load();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}