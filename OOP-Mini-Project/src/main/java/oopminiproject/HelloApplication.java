package oopminiproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopminiproject.dbmanagement.AdminDB;
import oopminiproject.dbmanagement.LogDB;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("farmerLogin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("CMS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        AdminDB.createAdminTable();
        AdminDB.registerDefaultAdmin();
        LogDB.createLogTable();
        launch();
    }
}
