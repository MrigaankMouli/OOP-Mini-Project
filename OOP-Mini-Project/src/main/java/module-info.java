module org.example.oopminiproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    exports oopminiproject.controller;

    opens oopminiproject.controller to javafx.fxml;
    exports oopminiproject;
}