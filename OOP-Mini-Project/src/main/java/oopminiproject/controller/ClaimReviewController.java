package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import oopminiproject.Claim;
import oopminiproject.Cow;
import oopminiproject.Farmer;
import oopminiproject.dbmanagement.ClaimDB;
import oopminiproject.dbmanagement.CowDB;
import oopminiproject.dbmanagement.FarmerDB;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;

import java.time.LocalDate;

//TODO: make a more comprehensive automation system (current one is yap)
public class ClaimReviewController {
    @FXML
    private TextArea claimDetailsArea;

    @FXML
    private TableView<Claim> claimsTable;
    @FXML
    private TableColumn<Claim, Integer> claimIDColumn;
    @FXML
    private TableColumn<Claim, LocalDate> claimDateColumn;
    @FXML
    private TableColumn<Claim, String> incidentTypeColumn;
    @FXML
    private TableColumn<Claim, String> farmerColumn;

    private Claim selectedClaim;

    @FXML
    private void initialize() {
        claimIDColumn.setCellValueFactory(new PropertyValueFactory<>("claimID"));
        claimDateColumn.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        incidentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("incidentType"));
        farmerColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        claimsTable.getItems().setAll(ClaimDB.getPendingClaims());

        claimsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            selectedClaim = newSelection;
            updateClaimDetailsArea();
        });
    }

    private void updateClaimDetailsArea() {
        if (selectedClaim == null) {
            claimDetailsArea.clear();
            return;
        }

        Farmer farmer = FarmerDB.getFarmer(selectedClaim.getUsername());
        Cow cow = CowDB.getCow(selectedClaim.getCowID());

        if (farmer == null || cow == null) {
            claimDetailsArea.setText("Farmer or Cow details could not be retrieved.");
            return;
        }

        String details = "Farmer Details:\n" +
                "Username: " + farmer.getUsername() + "\n" +
                "Full Name: " + farmer.getFullName() + "\n" +
                "Farm Address: " + farmer.getFarmAddress() + "\n\n" +

                "Cow Details:\n" +
                "Cow ID: " + cow.getId() + "\n" +
                "Breed: " + cow.getBreed() + "\n" +
                "Age: " + cow.getAge() + " years\n" +
                "Weight: " + cow.getWeight() + " kg\n" +
                "Vaccination status: " + cow.getVaccinationStatus() + "\n" +
                "Insurance: " + cow.getInsurance() + "\n" +
                "Owner: " + cow.getOwner() + "\n\n" +

                "Claim Details:\n" +
                "Claim ID: " + selectedClaim.getClaimID() + "\n" +
                "Incident Type: " + selectedClaim.getIncidentType() + "\n" +
                "Incident Description: " + selectedClaim.getIncidentDescription() + "\n" +
                "Incident Date: " + selectedClaim.getIncidentDate() + "\n" +
                "Claim Date: " + selectedClaim.getClaimDate() + "\n" +
                "Status: " + selectedClaim.getStatus() + "\n";

        claimDetailsArea.setText(details);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        FXUtils.swapScene(event, "adminDashboard-view.fxml");
    }

    @FXML
    private void handleVerification(ActionEvent event) {
        if (selectedClaim == null) return;

        Cow cow = CowDB.getCow(selectedClaim.getCowID());
        if (cow == null || selectedClaim.getInsurance().equals("None") ||
                !CowDB.getCowChecksum(selectedClaim.getCowID()).equals(SecurityUtils.cowHasher(cow))) {
            handleFail(event);
            return;
        }

        String insuranceType = selectedClaim.getInsurance();
        String incidentType = selectedClaim.getIncidentType();
        int cowWeight = cow.getWeight();
        LocalDate incidentDate = selectedClaim.getIncidentDate();
        LocalDate claimDate = selectedClaim.getClaimDate();
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(incidentDate, claimDate);

        boolean isApproved = false;

        switch (insuranceType) {
            case "LRP":  // Livestock Risk Protection
                if (incidentType.equals("loss") && daysBetween <= 30 && cowWeight > 200) {
                    isApproved = true;
                }
                break;

            case "CI": //Cattle insurance
                if (incidentType.equals("illness") && daysBetween <= 14 && cowWeight > 300) {
                    isApproved = true;
                }
                break;

            case "LGM":  // Livestock Gross Margin
                if (incidentType.equals("loss") && cowWeight > 250 && daysBetween <= 45) {
                    isApproved = true;
                } else if (incidentType.equals("death") && cowWeight > 500) {
                    isApproved = true;
                }
                break;

            case "YP":  // Yield Protection
                if (incidentType.equals("illness") && daysBetween <= 30 && cowWeight > 400) {
                    isApproved = true;
                } else if (incidentType.equals("loss") && daysBetween <= 30 && cowWeight > 300) {
                    isApproved = true;
                }
                break;

            default:
                System.out.println("Unknown insurance type");
                break;
        }

        if (isApproved) {
            ClaimDB.updateClaimStatus(selectedClaim.getClaimID(), "APPROVED");
        } else {
            handleFail(event);
        }

        claimsTable.getItems().setAll(ClaimDB.getPendingClaims());
    }


    @FXML
    private void handleFail(ActionEvent event) {
        ClaimDB.updateClaimStatus(selectedClaim.getClaimID(), "REJECTED");
        claimsTable.getItems().setAll(ClaimDB.getPendingClaims());
    }
}
