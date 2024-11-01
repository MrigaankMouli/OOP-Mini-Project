package oopminiproject.dbmanagement;

import oopminiproject.Farmer;
import oopminiproject.Log;
import oopminiproject.Session;
import oopminiproject.utility.SecurityUtils;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

//IMPLICIT ASSUMPTION ABOUT USER ENTITY: EITHER FARMER OR ADMIN. SO WHEN RELATED ENTITIES NOT AVAILABLE, NULLS PASSED
public class LogDB {
    private static final Logger LOGGER = Logger.getLogger(LogDB.class.getName());

    private static final String dbName = "logs";

    public static void createLogTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (" +
                "logID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "actionType TEXT NOT NULL, " +
                "relatedEntityID INTEGER, " +
                "relatedEntityType TEXT, " +
                "username TEXT, " +
                "checksum TEXT NOT NULL);";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Logs table created / already exists");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static void logAction(String actionType, Integer relatedEntityID, String relatedEntityType) {
        String username = Session.getInstance().getUsername();

        String toChecksum = actionType + username;
        if (relatedEntityID != null && relatedEntityType != null) toChecksum += relatedEntityType + relatedEntityID;
        String checksum = SecurityUtils.hash(toChecksum);

        String sql = "INSERT INTO logs(actionType, relatedEntityID, relatedEntityType, username, checksum) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, actionType);
            if (relatedEntityID != null) {
                preparedStatement.setInt(2, relatedEntityID);
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            if (relatedEntityType != null) {
                preparedStatement.setString(3, relatedEntityType);
            } else {
                preparedStatement.setNull(3, Types.VARCHAR);
            }
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, checksum);
            preparedStatement.executeUpdate();
            System.out.println("Action logged");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to log action: " + e.getMessage(), e);
        }
    }

    private static @NotNull List<Log> getLogList(String sql, @NotNull Consumer<PreparedStatement> statementHandler) {
        List<Log> logs = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            statementHandler.accept(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int logID = resultSet.getInt("logID");
                LocalDateTime time = resultSet.getTimestamp("timestamp").toLocalDateTime();
                String action = resultSet.getString("actionType");
                String username = resultSet.getString("username");
                String relatedEntity = resultSet.getString("relatedEntityType");
                int relatedEntityIDPrimitive = resultSet.getInt("relatedEntityID");
                Integer relatedEntityID = (resultSet.wasNull()) ? null : relatedEntityIDPrimitive;

                Log log = new Log(logID, time, action, username, relatedEntity, relatedEntityID);
                logs.add(log);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return logs;
    }

    public static @NotNull List<Log> getAllLogs() {
        String sql = "SELECT logID, timestamp, actionType, relatedEntityID, relatedEntityType, username FROM logs;";
        return getLogList(sql, preparedStatement -> {});
    }
}
