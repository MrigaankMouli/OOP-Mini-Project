package oopminiproject.controller;

import oopminiproject.HelloApplication;
import oopminiproject.Session;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.utility.FXUtils;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label currentUser;

    @FXML
    private void initialize() {
        currentUser.setText(Session.getInstance().getUsername());
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        LogDB.logAction("UEND", null, null);

        Session session = Session.getInstance();
        session.setUsername(null);
        session.setUserType(null);

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
    private void moveToInsuranceReview(ActionEvent event) {
        FXUtils.swapScene(event, "insuranceReview-view.fxml");
    }

    @FXML
    private void moveToInsuranceClaim(ActionEvent event) {
        FXUtils.swapScene(event, "insuranceClaim-view.fxml");
    }
}
