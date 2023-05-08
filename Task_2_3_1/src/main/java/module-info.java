module ru.nsu.fit.lylova.javafxsnake {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;


    exports ru.nsu.fit.lylova.javafxsnake;
    exports ru.nsu.fit.lylova.javafxsnake.cell;
    exports ru.nsu.fit.lylova.javafxsnake.cell.controller;
    opens ru.nsu.fit.lylova.console to javafx.fxml;
    exports ru.nsu.fit.lylova.console;
    opens ru.nsu.fit.lylova.javafxsnake to javafx.fxml;
    opens ru.nsu.fit.lylova.javafxsnake.cell to javafx.fxml;
    opens ru.nsu.fit.lylova.javafxsnake.cell.controller to javafx.fxml;
    opens ru.nsu.fit.lylova.javafxsnake.controller to javafx.fxml;
    exports ru.nsu.fit.lylova.javafxsnake.controller;



}