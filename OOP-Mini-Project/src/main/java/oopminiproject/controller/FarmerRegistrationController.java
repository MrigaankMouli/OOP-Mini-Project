package oopminiproject.controller;

import oopminiproject.*;
import oopminiproject.controller.*;
import oopminiproject.dbmanagement.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FarmerRegistrationController {
    private static final Logger LOGGER = Logger.getLogger(FarmerRegistrationController.class.getName());

    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField farmAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String fullName = fullNameField.getText();
        String farmAddress = farmAddressField.getText();
        String password = passwordField.getText();

        String hashedPassword = hashPassword(password);

        FarmerDB.createFarmerTable();

        FarmerDB.insertFarmer(username, fullName, farmAddress, hashedPassword);
    }

    private String hashPassword(String password) {
        String hashedPassword = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            hashedPassword = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return hashedPassword;
    }

    @FXML
    private void moveToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("farmerLogin-view.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene newScene = new Scene(loginRoot);
            currentStage.setScene(newScene);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
