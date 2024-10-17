module org.example.oopminiproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    exports oopminiproject.controller;

    opens oopminiproject.controller to javafx.fxml;
    exports oopminiproject;
}