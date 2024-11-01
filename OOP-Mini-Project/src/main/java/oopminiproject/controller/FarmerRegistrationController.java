package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import oopminiproject.Session;
import oopminiproject.dbmanagement.FarmerDB;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;
import oopminiproject.Log;

import java.util.regex.Pattern;

public class FarmerRegistrationController {

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

    public FarmerRegistrationController() {
        initializeLogging();
    }

    private void initializeLogging() {
        LogDB.createLogTable();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String fullName = fullNameField.getText();
        String farmAddress = farmAddressField.getText();
        String password = passwordField.getText();

        if (FarmerDB.isUsernameTaken(username)) {
            statusLabel.setText("Username already taken!");
            LogDB.logAction(new Log("Registration failed - username taken", String.valueOf(System.currentTimeMillis()), username));
            return;
        }

        if (!isPasswordStrong(password)) {
            statusLabel.setText("Password must:\n" +
                                " be at least 8 characters long\n" +
                                " and include uppercase, lowercase, digit,\n" +
                                " and special character.");
            LogDB.logAction(new Log("Registration failed - weak password", String.valueOf(System.currentTimeMillis()), username));
            return;
        }

        String hashedPassword = SecurityUtils.hash(password);

        FarmerDB.createFarmerTable();
        FarmerDB.insertFarmer(username, fullName, farmAddress, hashedPassword);

        Session session = Session.getInstance();
        session.setUsername(username);
        session.setUserType("FARMER");

        LogDB.logAction(new Log("Registration successful", String.valueOf(System.currentTimeMillis()), username));
        moveToDashboard(event);
    }

    private boolean isPasswordStrong(String password) {
        if (password.length() < 8) {
            return false;
        }
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=.{8,})";
        return Pattern.matches(passwordPattern, password);
    }

    @FXML
    private void moveToDashboard(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }

    @FXML
    private void moveToLogin(ActionEvent event) {
        FXUtils.swapScene(event, "farmerLogin-view.fxml");
    }
}
