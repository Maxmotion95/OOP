module ru.nsu.fit.lylova.javafxsanke {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.fit.lylova.javafxsanke to javafx.fxml;
    exports ru.nsu.fit.lylova.javafxsanke;
}