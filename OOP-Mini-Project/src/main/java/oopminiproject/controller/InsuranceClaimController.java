package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.time.LocalDate;

import javafx.scene.control.cell.PropertyValueFactory;
import oopminiproject.Claim;
import oopminiproject.Cow;
import oopminiproject.dbmanagement.ClaimDB;
import oopminiproject.utility.FXUtils;
import oopminiproject.dbmanagement.CowDB;
import oopminiproject.utility.SecurityUtils;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.Log;
import java.util.logging.Logger;
import java.util.logging.Level;

public class InsuranceClaimController {
    private static final Logger LOGGER = Logger.getLogger(InsuranceClaimController.class.getName());

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

        LogDB.createLogTable(); 
        LogDB.logAction(new Log("Initialized InsuranceClaimController", String.valueOf(System.currentTimeMillis()), "System"));
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

        ClaimDB.createClaimTable();

        if (SecurityUtils.cowHasher(cow).equals(CowDB.getCowChecksum(cowID))) {
            ClaimDB.insertClaim(cowID, insurance, incidentType, incidentDescription, incidentDate, claimDate, username);
            claimsTable.getItems().setAll(ClaimDB.getUserClaims());
            LogDB.logAction(new Log("Claim processed for cow ID: " + cowID + " by user: " + username, String.valueOf(System.currentTimeMillis()), username));
        } else {
            LOGGER.log(Level.WARNING, "Data corruption or tampering detected for cow with ID: " + cowID + 
                                       " by user: " + username + ". Claim was not processed.");
            LogDB.logAction(new Log("Claim attempt failed for cow ID: " + cowID + " due to data corruption/tampering by user: " + username, String.valueOf(System.currentTimeMillis()), username));
        }
    }
}
