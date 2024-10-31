package oopminiproject.controller;

import oopminiproject.Session;
import oopminiproject.dbmanagement.AdminDB; // Assuming you have a similar class for admin
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.util.logging.Logger;

public class AdminLoginController {
    private static final Logger LOGGER = Logger.getLogger(AdminLoginController.class.getName());

    @FXML
    private TextField adminUsernameField;
    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private void handleAdminLogin(ActionEvent event) {
        String username = adminUsernameField.getText();
        String password = adminPasswordField.getText();

        String hashedPassword = SecurityUtils.hash(password);
        String storedPassword = AdminDB.fetchPasswordHash(username);

        if (hashedPassword.equals(storedPassword)) {
            System.out.println("Admin auth successful");

            Session session = Session.getInstance();
            session.setUsername(username);
            moveToAdminDashboard(event); 
        } else {
            System.out.println("Admin auth failed");
            // TODO: add better messages.
        }
    }

    @FXML
    private void moveToFarmerLogin(ActionEvent event) {
        FXUtils.swapScene(event, "farmerLogin-view.fxml");
    }

    private void moveToAdminDashboard(ActionEvent event) {
        FXUtils.swapScene(event, "adminDashboard-view.fxml"); 
    }

    //NO ADMIN REGISTRATION PAGE!!! WE CANT LET FARMERS APPROVE THEIR OWN POLICIES!!
    //The system: we preload a test admin. the prod model includes an admin side client to register into DB
}
