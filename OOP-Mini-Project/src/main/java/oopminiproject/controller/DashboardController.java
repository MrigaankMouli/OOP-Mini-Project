package oopminiproject.controller;

import oopminiproject.HelloApplication;
import oopminiproject.Session;
import oopminiproject.utility.FXUtils;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import javax.swing.*;
import java.io.IOException;

import java.util.logging.Logger;
import java.util.logging.Level;

public class DashboardController {
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    @FXML
    private Label currentUser;

    @FXML
    private void initialize() {
        Session session = Session.getInstance();
        currentUser.setText(session.getUsername());
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Session session = Session.getInstance();
        session.setUsername(null);

        moveToLogin(event);
    }

    private void moveToLogin(ActionEvent event) {
        FXUtils.swapScene(event, "farmerLogin-view.fxml");
    }

    @FXML
    private void moveToCattleManager(ActionEvent event) {
        FXUtils.swapScene(event, "cattleManager-view.fxml");
    }

    @FXML
    private void moveToInsuranceReview() {

    }

    @FXML
    private void moveToInsuranceClaim() {

    }
}
