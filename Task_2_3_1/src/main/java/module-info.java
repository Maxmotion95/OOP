module ru.nsu.fit.lylova.javafxsnake {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;


    opens ru.nsu.fit.lylova.javafxsnake to javafx.fxml;
    exports ru.nsu.fit.lylova.javafxsnake;
    exports ru.nsu.fit.lylova.javafxsnake.cellControllers;
    opens ru.nsu.fit.lylova.javafxsnake.cellControllers to javafx.fxml;
}