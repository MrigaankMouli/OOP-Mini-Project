package oopminiproject.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import oopminiproject.HelloApplication;
import org.jetbrains.annotations.NotNull;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
            if (!newvalue.matches("\\d*")) {
                textField.setText(newvalue.replaceAll("\\D", ""));
            }
        });
    }

    //only reads .txt files in resources folder
    public static void readTextToTextArea(TextArea textArea, String filename) {
        try (InputStream inputStream = HelloApplication.class.getResourceAsStream("/oopminiproject/" + filename)) {
            if (inputStream == null) {
                textArea.setText("File not found");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String content = reader.lines().collect(Collectors.joining("\n"));
                textArea.setText(content);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error reading file content: " + e.getMessage(), e);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error accessing file: " + e.getMessage(), e);
        }
    }
  
    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
