package oopminiproject.controller;

import oopminiproject.*;
import oopminiproject.dbmanagement.*;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.PasswordUtils;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.util.logging.Logger;
import java.util.logging.Level;

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

        String hashedPassword = PasswordUtils.hashPassword(password);
        String storedPassword = FarmerDB.fetchPasswordHash(username);
        if (hashedPassword.equals(storedPassword)) {
            System.out.println("Auth successful");

            Session session = Session.getInstance();
            session.setUsername(username);
            moveToDashboard(event);
        } else
            System.out.println("Auth failed");

        //TODO: add better user facing messages. "Wrong password" etc
    }

    @FXML
    private void moveToRegister(ActionEvent event) {
        FXUtils.swapScene(event, "farmerRegistration-view.fxml");
    }

    private void moveToDashboard(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }

    //TODO: Add ADMIN LOGIN page.
}
