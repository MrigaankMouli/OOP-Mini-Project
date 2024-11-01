package oopminiproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import oopminiproject.Log;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.utility.FXUtils;

import java.time.LocalDateTime;

public class LogController {

    @FXML
    private TableView<Log> logTable;
    @FXML
    private TableColumn<Log, Integer> logIDColumn;
    @FXML
    private TableColumn<Log, LocalDateTime> timeColumn;
    @FXML
    private TableColumn<Log, String> actionColumn;
    @FXML
    private TableColumn<Log, String> usernameColumn;
    @FXML
    private TableColumn<Log, String> relatedEntityColumn;
    @FXML
    private TableColumn<Log, Integer> relatedEntityIDColumn;

    @FXML
    private void initialize() {
        logIDColumn.setCellValueFactory(new PropertyValueFactory<>("logID"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        relatedEntityColumn.setCellValueFactory(new PropertyValueFactory<>("relatedEntity"));
        relatedEntityIDColumn.setCellValueFactory(new PropertyValueFactory<>("relatedEntityID"));
        logTable.getItems().setAll(LogDB.getAllLogs());
    }

    @FXML
    private void handleBack(ActionEvent event) {
        FXUtils.swapScene(event, "adminDashboard-view.fxml");
    }
}
