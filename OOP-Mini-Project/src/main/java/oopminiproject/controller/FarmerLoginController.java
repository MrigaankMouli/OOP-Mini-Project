package oopminiproject.controller;

import oopminiproject.*;
import oopminiproject.dbmanagement.*;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class FarmerLoginController {
    private static final Logger LOGGER = Logger.getLogger(FarmerLoginController.class.getName());

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String hashedPassword = SecurityUtils.hash(password);
        String storedPassword = FarmerDB.fetchPasswordHash(username);
        if (hashedPassword.equals(storedPassword)) {
            System.out.println("Login successful");

            LogDB.logAction(new Log("Successful login for user: " + username, String.valueOf(System.currentTimeMillis()).toString(), username));

            Session session = Session.getInstance();
            session.setUsername(username);
            session.setUserType("FARMER");
            moveToDashboard(event);
        } else {
            System.out.println("Login Failed - Invalid Username or Password");
            LogDB.logAction(new Log("Failed login attempt for user: " + username, String.valueOf(System.currentTimeMillis()).toString(), username));
        }
    }

    @FXML
    private void moveToRegister(ActionEvent event) {
        FXUtils.swapScene(event, "farmerRegistration-view.fxml");
    }

    @FXML
    private void moveToAdminLogin(ActionEvent event) {
        FXUtils.swapScene(event, "adminLogin-view.fxml");
    }

    private void moveToDashboard(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }
}
