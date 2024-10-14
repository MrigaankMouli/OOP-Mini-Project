module org.example.oopminiproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens oopminiproject to javafx.fxml;
    exports oopminiproject;
    exports oopminiproject.controller;
}