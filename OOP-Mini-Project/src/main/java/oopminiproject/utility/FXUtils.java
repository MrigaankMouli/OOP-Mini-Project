package oopminiproject.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oopminiproject.HelloApplication;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXUtils {
    private static final Logger LOGGER = Logger.getLogger(FXUtils.class.getName());

    public static void swapScene(@NotNull ActionEvent event, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(resource));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene loginScene = new Scene(root);
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static void forceNumericTextField(@NotNull TextField textField) {
        textField.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            if (!newvalue.matches("\\d*"))
                textField.setText(newvalue.replaceAll("\\D", ""));
        });
    }
}
