package oopminiproject.controller;

import oopminiproject.HelloApplication;
import oopminiproject.Session;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

import java.util.logging.Logger;
import java.util.logging.Level;

public class DashboardController {
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    @FXML
    private Label currentUser;

    @FXML
    public void initialize() {
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
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("farmerLogin-view.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @FXML
    private void moveToCattleManager() {

    }

    @FXML
    private void moveToInsuranceReview() {

    }

    @FXML
    private void moveToInsuranceClaim() {

    }
}
