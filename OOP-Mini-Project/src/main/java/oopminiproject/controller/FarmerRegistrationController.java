package oopminiproject.controller;

import javafx.event.ActionEvent;
import oopminiproject.dbmanagement.*;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.logging.Logger;

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

        String hashedPassword = SecurityUtils.hash(password);

        FarmerDB.createFarmerTable();

        FarmerDB.insertFarmer(username, fullName, farmAddress, hashedPassword);
    }

    @FXML
    private void moveToLogin(ActionEvent event) {
        FXUtils.swapScene(event, "farmerLogin-view.fxml");
    }
}
