package oopminiproject.controller;

import oopminiproject.Session;
import oopminiproject.utility.FXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminDashboardController {

    @FXML
    private Label currentAdminUser;

    @FXML
    private void initialize() {
        currentAdminUser.setText(Session.getInstance().getUsername());
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Session session = Session.getInstance();
        session.setUsername(null);
        session.setUserType(null);

        moveToLogin(event);
    }

    private void moveToLogin(ActionEvent event) {
        FXUtils.swapScene(event, "adminLogin-view.fxml");
    }

    @FXML
    private void moveToClaimReview(ActionEvent event) {
        FXUtils.swapScene(event, "claimReview-view.fxml");
    }

    @FXML
    private void moveToLogs(ActionEvent event) {
    }
}
