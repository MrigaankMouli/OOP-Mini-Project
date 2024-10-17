package oopminiproject.controller;

import oopminiproject.*;
import oopminiproject.dbmanagement.*;
import oopminiproject.utility.PasswordUtils;

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

        String hashedPassword = PasswordUtils.hashPassword(password);
        String storedPassword = FarmerDB.fetchPasswordHash(username);
        if (hashedPassword.equals(storedPassword)) {
            System.out.println("Auth successful");

            Session session = Session.getInstance();
            session.setUsername(username);
            moveToDashboard();
        } else
            System.out.println("Auth failed");

        //TODO: add better user facing messages. "Wrong password" etc
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

    private void moveToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            Parent dashboardRoot = loader.load();
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene newScene = new Scene(dashboardRoot);
            currentStage.setScene(newScene);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    //TODO: Add ADMIN LOGIN page.
}
