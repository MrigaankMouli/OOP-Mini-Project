package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import oopminiproject.Session;
import oopminiproject.dbmanagement.*;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;
import java.util.logging.Logger;

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
    private Label statusLabel;

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String fullName = fullNameField.getText();
        String farmAddress = farmAddressField.getText();
        String password = passwordField.getText();

        if (FarmerDB.isUsernameTaken(username)) {
            statusLabel.setText("Username already taken!");
            return;
        }

        if (!isPasswordStrong(password)) {
            statusLabel.setText("Password must:\n" +
                                " be at least 8 characters long\n" +
                                " and include uppercase, lowercase, digit,\n" +
                                " and special character.");
            return;
        }

        String hashedPassword = SecurityUtils.hash(password);

        FarmerDB.createFarmerTable();
        FarmerDB.insertFarmer(username, fullName, farmAddress, hashedPassword);

        Session session = Session.getInstance();
        session.setUsername(username);
        session.setUserType("FARMER");
        moveToDashboard(event);
    }

    private boolean isPasswordStrong(String password) {
        if (password.length() < 8) {
            return false;
        }
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=.{8,})";
        return Pattern.matches(passwordPattern, password);
    }

    private void moveToDashboard(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }

    @FXML
    private void moveToLogin(ActionEvent event) {
        FXUtils.swapScene(event, "farmerLogin-view.fxml");
    }
}
