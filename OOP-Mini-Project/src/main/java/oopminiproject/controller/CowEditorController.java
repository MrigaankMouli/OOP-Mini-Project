package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import oopminiproject.Cow;
import oopminiproject.Farmer;
import oopminiproject.Session;
import oopminiproject.dbmanagement.CowDB;
import oopminiproject.dbmanagement.FarmerDB;
import oopminiproject.utility.FXUtils;

public class CowEditorController {
    @FXML
    private TextField newAgeField;

    @FXML
    private TextField newWeightField;

    private final ToggleGroup vaccinationStatusGroup = new ToggleGroup();
    @FXML
    private RadioButton notVaccinated;
    @FXML
    private RadioButton partiallyVaccinated;
    @FXML
    private RadioButton fullyVaccinated;

    @FXML
    private CheckBox allowTransferBox;

    @FXML
    private TableView<Farmer> farmersTable;
    @FXML
    private TableColumn<Farmer, String> usernameColumn;
    @FXML
    private TableColumn<Farmer, String> fullNameColumn;
    @FXML
    private TableColumn<Farmer, String> farmAddressColumn;

    private Farmer selectedFarmer;

    private static Cow cow;

    public static void setCow(Cow passedCow) {
        cow = passedCow;
    }

    @FXML
    private void initialize() {
        notVaccinated.setToggleGroup(vaccinationStatusGroup);
        partiallyVaccinated.setToggleGroup(vaccinationStatusGroup);
        fullyVaccinated.setToggleGroup(vaccinationStatusGroup);

        FXUtils.forceNumericTextField(newAgeField);
        FXUtils.forceNumericTextField(newWeightField);

        newAgeField.setText(Integer.toString(cow.getAge()));
        newWeightField.setText(Integer.toString(cow.getWeight()));

        if (cow.getVaccinationStatus().equals("None")) notVaccinated.setSelected(true);
        else if (cow.getVaccinationStatus().equals("Partial")) partiallyVaccinated.setSelected(true);
        else fullyVaccinated.setSelected(true);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        farmAddressColumn.setCellValueFactory(new PropertyValueFactory<>("farmAddress"));
        farmersTable.getItems().addAll(FarmerDB.getAllFarmers());

        farmersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> selectedFarmer = newSelection);
    }

    @FXML
    private void handleEdits(ActionEvent event) {
        int cowID = cow.getId();
        int newAge = Integer.parseInt(newAgeField.getText());
        int newWeight = Integer.parseInt(newWeightField.getText());
        String newCowVaccinationStatus;
        Toggle selected = vaccinationStatusGroup.getSelectedToggle();
        if (selected == notVaccinated) newCowVaccinationStatus = "None";
        else if (selected == partiallyVaccinated) newCowVaccinationStatus = "Partial";
        else newCowVaccinationStatus = "Full";
        if (selectedFarmer == null) selectedFarmer = FarmerDB.getFarmer(Session.getInstance().getUsername());
        String newOwnerUsername = selectedFarmer.getUsername();

        if (cow.getAge() != newAge) CowDB.updateCowAge(cowID, newAge);
        if (cow.getWeight() != newWeight) CowDB.updateCowWeight(cowID, newWeight);
        if (!cow.getVaccinationStatus().equals(newCowVaccinationStatus)) CowDB.updateCowVaccinationStatus(cowID, newCowVaccinationStatus);
        if (!cow.getOwner().equals(newOwnerUsername) && allowTransferBox.isSelected()) CowDB.updateCowOwner(cowID, newOwnerUsername);

        handleBack(event);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        FXUtils.swapScene(event, "cattleManager-view.fxml");
    }
}
