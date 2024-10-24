package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oopminiproject.Cow;

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

    @FXML
    private Label currentUser;

    @FXML
    private void cowSelected(ActionEvent event) {
    }

    @FXML
    private void handleBack(ActionEvent event) {
    }
}