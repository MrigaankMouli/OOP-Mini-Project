package oopminiproject.dbmanagement;

import oopminiproject.Log;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LogDB {
    private static final Logger LOGGER = Logger.getLogger(LogDB.class.getName());
    private static final String dbName = "logs";

    public static void createLogTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "action TEXT NOT NULL, " +
                     "timestamp TEXT NOT NULL, " +
                     "username TEXT NOT NULL);";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Log table created / already exists");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static void logAction(Log log) {
        String sql = "INSERT INTO logs(action, timestamp, username) VALUES(?, ?, ?);";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, log.getAction());
            preparedStatement.setString(2, log.getTimestamp());
            preparedStatement.setString(3, log.getUsername());
            preparedStatement.executeUpdate();
            System.out.println("Log entry added: " + log.getAction());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static List<Log> fetchLogs() {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT action, timestamp, username FROM logs;";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String action = resultSet.getString("action");
                String timestamp = resultSet.getString("timestamp");
                String username = resultSet.getString("username");
                logs.add(new Log(action, timestamp, username));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return logs;
    }
}
