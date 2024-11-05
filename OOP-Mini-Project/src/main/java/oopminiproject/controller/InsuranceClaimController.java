package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.time.LocalDate;

import javafx.scene.control.cell.PropertyValueFactory;
import oopminiproject.Claim;
import oopminiproject.Cow;
import oopminiproject.dbmanagement.ClaimDB;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.utility.FXUtils;
import oopminiproject.dbmanagement.CowDB;
import oopminiproject.utility.SecurityUtils;

public class InsuranceClaimController {
    @FXML
    private DatePicker incidentDatePicker;

    @FXML
    private TextField cowIDField;

    @FXML
    private ComboBox<String> insuranceClaimBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TableView<Claim> claimsTable;
    @FXML
    private TableColumn<Claim, Integer> claimIDColumn;
    @FXML
    private TableColumn<Claim, String> cowIDColumn;
    @FXML
    private TableColumn<Claim, String> insuranceColumn;
    @FXML
    private TableColumn<Claim, LocalDate> incidentDateColumn;
    @FXML
    private TableColumn<Claim, LocalDate> claimDateColumn;
    @FXML
    private TableColumn<Claim, String> incidentTypeColumn;
    @FXML
    private TableColumn<Claim, String> statusColumn;

    @FXML
    private void initialize() {
        insuranceClaimBox.getItems().removeAll(insuranceClaimBox.getItems());
        insuranceClaimBox.getItems().addAll("loss", "illness", "death");

        FXUtils.forceNumericTextField(cowIDField);

        claimIDColumn.setCellValueFactory(new PropertyValueFactory<>("claimID"));
        cowIDColumn.setCellValueFactory(new PropertyValueFactory<>("cowID"));
        insuranceColumn.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        incidentDateColumn.setCellValueFactory(new PropertyValueFactory<>("incidentDate"));
        claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        incidentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("incidentType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        claimsTable.getItems().setAll(ClaimDB.getUserClaims());
    }

    @FXML
    private void handleBack(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }

    @FXML
    private void handleClaim(ActionEvent event) {
        int cowID = Integer.parseInt(cowIDField.getText());
        String incidentType = insuranceClaimBox.getSelectionModel().getSelectedItem();
        LocalDate incidentDate = incidentDatePicker.getValue();
        String incidentDescription = descriptionTextArea.getText();
        Cow cow = CowDB.getCow(cowID);
        assert cow != null;
        String insurance = cow.getInsurance();
        LocalDate claimDate = LocalDate.now();
        String username = cow.getOwner();

        if (SecurityUtils.cowHasher(cow).equals(CowDB.getCowChecksum(cowID))) {
            ClaimDB.createClaimTable();
            ClaimDB.insertClaim(cowID, insurance, incidentType, incidentDescription, incidentDate, claimDate, username);
            claimsTable.getItems().setAll(ClaimDB.getUserClaims());
            LogDB.logAction("CCOW", cowID, "Cow");
        } else {
            System.out.println("Cow data corrupted/tampered");
            LogDB.logAction("FCHS", cowID, "Cow");
        }
    }
}
