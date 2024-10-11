module org.example.oopminiproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.oopminiproject to javafx.fxml;
    exports org.example.oopminiproject;
}