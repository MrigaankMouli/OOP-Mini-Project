package oopminiproject.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import oopminiproject.Session;
import oopminiproject.dbmanagement.CowDB;
import oopminiproject.utility.FXUtils;
import oopminiproject.utility.SecurityUtils;

public class CattleManagerController {

    @FXML
    private ComboBox<String> cowInsuranceBox;

    @FXML
    private ComboBox<String> cowBreedBox;

    @FXML
    private TextField cowAgeField;

    @FXML
    private TextField cowWeightField;

    @FXML
    private RadioButton notVaccinated;

    @FXML
    private RadioButton partiallyVaccinated;

    @FXML
    private RadioButton fullyVaccinated;

    ToggleGroup vaccinationStatusGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        cowBreedBox.getItems().removeAll(cowBreedBox.getItems());
        cowBreedBox.getItems().addAll("Sindhi", "Tharparkar", "Gir", "Kankrej", "Hariana",
                                        "Ongole", "Sahiwal", "Deoni", "Other");

        cowInsuranceBox.getItems().removeAll(cowInsuranceBox.getItems());
        cowInsuranceBox.getItems().addAll("LRP", "CI", "LGM", "YP");
        //LRP: Livestock Risk Protection
        //CI: Cattle insurance
        //LGM: Livestock Gross Margin
        //YP: Yield Protection

        notVaccinated.setToggleGroup(vaccinationStatusGroup);
        partiallyVaccinated.setToggleGroup(vaccinationStatusGroup);
        fullyVaccinated.setToggleGroup(vaccinationStatusGroup);

        FXUtils.forceNumericTextField(cowAgeField);
        FXUtils.forceNumericTextField(cowWeightField);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }

    @FXML
    private void handleCowRegister(ActionEvent actionEvent) {
        String cowBreed = cowBreedBox.getSelectionModel().getSelectedItem();
        int cowAge = Integer.parseInt(cowAgeField.getText());
        int cowWeight = Integer.parseInt(cowWeightField.getText());
        String cowInsurance = cowInsuranceBox.getSelectionModel().getSelectedItem();

        String cowVaccinationStatus;
        Toggle selected = vaccinationStatusGroup.getSelectedToggle();
        if (selected == notVaccinated) cowVaccinationStatus = "None";
        else if (selected == partiallyVaccinated) cowVaccinationStatus = "Partial";
        else cowVaccinationStatus = "Full";
        //works, but in hindsight there must be a cleverer way / just using a combo box
        //TODO: make this nicer

        String cowOwner = Session.getInstance().getUsername();
        System.out.println(cowOwner);

        CowDB.createCowTable();
        CowDB.insertCow(cowBreed, cowAge, cowWeight, cowInsurance, cowVaccinationStatus, cowOwner);
    }
}
