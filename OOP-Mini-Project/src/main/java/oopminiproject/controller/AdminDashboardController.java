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
        moveToLogin(event);
    }

    private void moveToLogin(ActionEvent event) {
        FXUtils.swapScene(event, "adminLogin-view.fxml");
    }

    @FXML
    private void moveToFarmerManagement(ActionEvent event) {
        FXUtils.swapScene(event, "farmerManagement-view.fxml");
    }
    //TODO: Add Farmer Management Page(Review User, Delete User, Update User etc I dunno what admins do)

    @FXML
    private void moveToInsurancePolicyManagement(ActionEvent event) {
        FXUtils.swapScene(event, "insurancePolicyReview-view.fxml");
    }
    //TODO : Add Policy Review Page
}
