package oopminiproject.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import oopminiproject.Session;
import oopminiproject.dbmanagement.CowDB;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.utility.FXUtils;
import oopminiproject.Cow;

import java.util.Comparator;
import java.util.List;

public class CattleManagerController {

    @FXML
    private ComboBox<String> cowInsuranceBox;

    @FXML
    private ComboBox<String> cowBreedBox;

    @FXML
    private TextField cowAgeField;

    private final ToggleGroup vaccinationStatusGroup = new ToggleGroup();
    @FXML
    private TextField cowWeightField;

    @FXML
    private RadioButton notVaccinated;
    @FXML
    private RadioButton partiallyVaccinated;
    @FXML
    private RadioButton fullyVaccinated;

    @FXML
    private TableView<Cow> ownedCowsTable;
    @FXML
    private TableColumn<Cow, String> idColumn;
    @FXML
    private TableColumn<Cow, String> breedColumn;
    @FXML
    private TableColumn<Cow, Integer> ageColumn;
    @FXML
    private TableColumn<Cow, Integer> weightColumn;
    @FXML
    private TableColumn<Cow, String> insuranceColumn;
    @FXML
    private TableColumn<Cow, String> vaccinationStatusColumn;

    private Cow selectedCow;

    @FXML
    private void initialize() {
        cowBreedBox.getItems().removeAll(cowBreedBox.getItems());
        cowBreedBox.getItems().addAll("Sindhi", "Tharparkar", "Gir", "Kankrej", "Hariana",
                                        "Ongole", "Sahiwal", "Deoni", "Other");

        cowInsuranceBox.getItems().removeAll(cowInsuranceBox.getItems());
        cowInsuranceBox.getItems().addAll("LRP", "CI", "LGM", "YP", "None");
        //LRP: Livestock Risk Protection
        //CI: Cattle insurance
        //LGM: Livestock Gross Margin
        //YP: Yield Protection

        notVaccinated.setToggleGroup(vaccinationStatusGroup);
        partiallyVaccinated.setToggleGroup(vaccinationStatusGroup);
        fullyVaccinated.setToggleGroup(vaccinationStatusGroup);

        FXUtils.forceNumericTextField(cowAgeField);
        FXUtils.forceNumericTextField(cowWeightField);

        TableController();

        ownedCowsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> selectedCow = newSelection);
    }

    private void TableController() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        insuranceColumn.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        vaccinationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationStatus"));

        ownedCowsTable.getItems().setAll(CowDB.getOwnedCows());
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

        String cowOwner = Session.getInstance().getUsername();
        System.out.println(cowOwner);

        CowDB.createCowTable();
        CowDB.insertCow(cowBreed, cowAge, cowWeight, cowInsurance, cowVaccinationStatus, cowOwner);

        List<Cow> ownedCows = CowDB.getOwnedCows();
        ownedCowsTable.getItems().setAll(ownedCows);

        Cow newCow = ownedCows.stream().max(Comparator.comparingInt(Cow::getId)).orElse(null);
        assert newCow != null;
        LogDB.logAction("RCOW", newCow.getId(), "Cow");
    }

    public void handleEditRequest(ActionEvent event) {
        if (selectedCow != null) {
            CowEditorController.setCow(selectedCow);
            FXUtils.swapScene(event, "cowEditor-view.fxml");
        } else {
            System.out.println("No cow selected!");
        }
    }
}
