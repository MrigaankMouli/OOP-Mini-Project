package oopminiproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import oopminiproject.Log;
import oopminiproject.dbmanagement.LogDB;
import oopminiproject.utility.FXUtils;

import java.util.List;

public class LogViewController {
    @FXML
    private TableView<Log> logsTable;
    @FXML
    private TableColumn<Log, String> actionColumn;
    @FXML
    private TableColumn<Log, String> timestampColumn;
    @FXML
    private TableColumn<Log, String> usernameColumn;

    @FXML
    private void initialize() {
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        loadLogs();
    }

    private void loadLogs() {
        List<Log> logs = LogDB.fetchLogs();
        logsTable.getItems().setAll(logs);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        FXUtils.swapScene(event, "dashboard-view.fxml");
    }
}
