package oopminiproject.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import oopminiproject.Cow;
import oopminiproject.Session;
import oopminiproject.dbmanagement.CowDB;
import oopminiproject.utility.FXUtils;

//TODO: Handle TextAreas here only, take the blahs out of the FXML file later.
public class InsuranceReviewController {

    @FXML
    private Accordion insuranceAccordion;
    @FXML
    private TitledPane lrpPane;
    @FXML
    private TextArea lrpText;
    @FXML
    private TitledPane ciPane;
    @FXML
    private TextArea ciText;
    @FXML
    private TitledPane lgmPane;
    @FXML
    private TextArea lgmText;
    @FXML
    private TitledPane ypPane;
    @FXML
    private TextArea ypText;

    @FXML
    private TableView<Cow> uninsuredCowsTable;
    @FXML
    private TableColumn<Cow, String> idColumn;
    @FXML
    private TableColumn<Cow, String> breedColumn;
    @FXML
    private TableColumn<Cow, Integer> ageColumn;
    @FXML
    private TableColumn<Cow, Integer> weightColumn;
    @FXML
    private TableColumn<Cow, String> vaccinationStatusColumn;

    @FXML
    private Label currentUser;

    @FXML
    private TextField cowPremiumField;

    private Cow selectedCow = null;
    private TitledPane selectedPane = null;

    @FXML
    private void initialize() {
        currentUser.setText(Session.getInstance().getUsername());

        FXUtils.forceNumericTextField(cowPremiumField);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        vaccinationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationStatus"));

        uninsuredCowsTable.getItems().setAll(CowDB.getUninsuredCows());

        insuranceAccordion.expandedPaneProperty().addListener((observable, oldPane, newPane) -> {
            selectedPane = newPane;
            updatePremiumField();
        });

        uninsuredCowsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            selectedCow = newSelection;
            updatePremiumField();
        });
    }

    @FXML
    private void updatePremiumField() {
        if (selectedCow != null && selectedPane != null) {
            if (selectedPane == lrpPane)
                cowPremiumField.setText(String.valueOf(selectedCow.calculateLRPPremium()));
            else if (selectedPane == ciPane)
                cowPremiumField.setText(String.valueOf(selectedCow.calculateCIPremium()));
            else if (selectedPane == lgmPane)
                cowPremiumField.setText(String.valueOf(selectedCow.calculateLGMPremium()));
            else if (selectedPane == ypPane)
                cowPremiumField.setText(String.valueOf(selectedCow.calculateYPPremium()));
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }

    @FXML
    private void handleInsuranceApplication(ActionEvent event) {
        if (selectedCow != null && selectedPane != null) {
            String insurance = "";
            if (selectedPane == lrpPane) insurance = "LRP";
            else if (selectedPane == ciPane) insurance = "CI";
            else if (selectedPane == lgmPane) insurance = "LGM";
            else if (selectedPane == ypPane) insurance = "YP";
            CowDB.updateCowInsurance(selectedCow.getId(), insurance);
            uninsuredCowsTable.getItems().setAll(CowDB.getUninsuredCows());
        }
    }
}