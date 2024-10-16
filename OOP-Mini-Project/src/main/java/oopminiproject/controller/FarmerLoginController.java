package oopminiproject.controller;

import oopminiproject.*;
import oopminiproject.controller.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;
import java.util.logging.Level;

public class FarmerLoginController {
    private static final Logger LOGGER = Logger.getLogger(FarmerLoginController.class.getName());

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        //TODO: compare to stored password and see if valid
    }

    @FXML
    private void moveToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("farmerRegistration-view.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene newScene = new Scene(loginRoot);
            currentStage.setScene(newScene);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    //TODO: Add ADMIN LOGIN page.
}
