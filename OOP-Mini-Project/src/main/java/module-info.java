module org.example.oopminiproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.logging;

    opens oopminiproject.controller to javafx.fxml;
    exports oopminiproject;
}