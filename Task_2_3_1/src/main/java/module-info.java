module ru.nsu.fit.lylova.javafxsnake {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.fit.lylova.javafxsnake to javafx.fxml;
    exports ru.nsu.fit.lylova.javafxsnake;
}