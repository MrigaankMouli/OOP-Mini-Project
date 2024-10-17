package oopminiproject.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CattleManagerController {

    @FXML
    private ComboBox<String> cowInsurancePolicy;
    @FXML
    private ComboBox<String> cowBreed;
    @FXML
    private DatePicker cowBirthDate;
    @FXML
    private TextField cowWeight;
    @FXML
    private RadioButton notVaccinated;
    @FXML
    private RadioButton partiallyVaccinated;
    @FXML
    private RadioButton fullyVaccinated;



    @FXML
    private void handleCowRegister(ActionEvent actionEvent) {
    }
}
