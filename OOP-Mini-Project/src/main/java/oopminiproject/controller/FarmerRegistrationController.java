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

//TODO: Implement status messages "username taken", etc.
//TODO: Make it look nice!!
//both of these are low priority rn. The reg page works, that's what's important.

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

        String hashedPassword = PasswordUtils.hashPassword(password);

        FarmerDB.createFarmerTable();

        FarmerDB.insertFarmer(username, fullName, farmAddress, hashedPassword);
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
