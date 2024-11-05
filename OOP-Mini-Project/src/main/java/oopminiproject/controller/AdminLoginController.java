package oopminiproject.controller;

import oopminiproject.Session;
import oopminiproject.dbmanagement.AdminDB;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class AdminLoginController {

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
            LogDB.logAction("ALOG", null, null);
            moveToAdminDashboard(event); 
        } else {
            System.out.println("Admin auth failed");
            LogDB.logAction("ALGF", null, null);
        }
    }

    @FXML
    private void moveToFarmerLogin(ActionEvent event) {
        FXUtils.swapScene(event, "farmerLogin-view.fxml");
    }

    private void moveToAdminDashboard(ActionEvent event) {
        FXUtils.swapScene(event, "adminDashboard-view.fxml"); 
    }
}
