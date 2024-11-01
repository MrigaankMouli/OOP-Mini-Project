package oopminiproject.dbmanagement;

import oopminiproject.Claim;
import oopminiproject.Session;
import oopminiproject.utility.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClaimDB {
    private static final Logger LOGGER = Logger.getLogger(ClaimDB.class.getName());

    private static final String dbName = "claims";

    public static void createClaimTable() {
        String sql = "CREATE TABLE IF NOT EXISTS claims (" +
                "claimID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cowID INT NOT NULL, " +
                "insurance TEXT NOT NULL, " +
                "incidentType TEXT NOT NULL, " +
                "incidentDescription TEXT, " +
                "incidentDate DATE NOT NULL, " +
                "claimDate DATE NOT NULL, " +
                "username TEXT NOT NULL, " +
                "status TEXT NOT NULL, " +
                "checksum TEXT NOT NULL " +
                ");";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Claim table created / already exists");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        //TODO: generalize the table creation method in utilities
    }

    public static void insertClaim(int cowID, String insurance, String incidentType, String incidentDescription,
                                   LocalDate incidentDate, LocalDate claimDate, String username) {
        String sql = "INSERT INTO claims(cowID, insurance, incidentType, incidentDescription, incidentDate, claimDate, username, status, checksum) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String status = "PENDING"; //default status

        String checksum = SecurityUtils.hash(cowID + insurance + incidentType + incidentDate + claimDate + username + status);

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cowID);
            preparedStatement.setString(2, insurance);
            preparedStatement.setString(3, incidentType);
            preparedStatement.setString(4, incidentDescription);
            preparedStatement.setDate(5, Date.valueOf(incidentDate));
            preparedStatement.setDate(6, Date.valueOf(claimDate));
            preparedStatement.setString(7, username);
            preparedStatement.setString(8, status);  // Setting status field
            preparedStatement.setString(9, checksum);

            preparedStatement.executeUpdate();
            System.out.println("Data inserted to ClaimsDB");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private static @NotNull List<Claim> getClaimList(String sql, @NotNull Consumer<PreparedStatement> statementHandler) {
        List<Claim> claims = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            statementHandler.accept(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int claimID = resultSet.getInt("claimID");
                int cowID = resultSet.getInt("cowID");
                String insurance = resultSet.getString("insurance");
                String incidentType = resultSet.getString("incidentType");
                String incidentDescription = resultSet.getString("incidentDescription");
                LocalDate incidentDate = resultSet.getDate("incidentDate").toLocalDate();
                LocalDate claimDate = resultSet.getDate("claimDate").toLocalDate();
                String username = resultSet.getString("username");
                String status = resultSet.getString("status");

                String calculatedHash = SecurityUtils.hash(cowID + insurance + incidentType + incidentDate + claimDate + username + status);
                if (calculatedHash.equals(resultSet.getString("checksum"))) {
                    Claim claim = new Claim(claimID, cowID, insurance, incidentType, incidentDescription, incidentDate, claimDate, username, status);
                    claims.add(claim);
                } else {
                    LOGGER.log(Level.WARNING, "Checksum mismatch for claim ID: " + claimID);
                    LogDB.logAction("FCHS", claimID, "Claim");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return claims;
    }

    @NotNull
    public static List<Claim> getPendingClaims() {
        String sql = "SELECT claimID, cowID, insurance, incidentType, incidentDescription, incidentDate, claimDate, username, status, checksum " +
                "FROM claims " +
                "WHERE status = 'PENDING';";

        return getClaimList(sql, preparedStatement -> {});
    }


    @NotNull
    public static List<Claim> getUserClaims() {
        String username = Session.getInstance().getUsername();

        String sql = "SELECT claimID, cowID, insurance, incidentType, incidentDescription, incidentDate, claimDate, username, status, checksum " +
                "FROM claims " +
                "WHERE username = ?;";

        return getClaimList(sql, preparedStatement -> {
            try {
                preparedStatement.setString(1, username);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        });
    }

    public static @Nullable Claim getClaimByID(int claimID) {
        String sql = "SELECT claimID, cowID, insurance, incidentType, incidentDescription, incidentDate, claimDate, username, status, checksum " +
                "FROM claims WHERE claimID = ?";

        List<Claim> claims = getClaimList(sql, preparedStatement -> {
            try {
                preparedStatement.setInt(1, claimID);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        });

        return claims.isEmpty() ? null : claims.get(0);
    }


    public static void updateClaimStatus(int claimID, String newStatus) {
        String sql = "UPDATE claims SET status = ?, checksum = ? WHERE claimID = ?";

        Claim claim = getClaimByID(claimID);
        if (claim == null) {
            LOGGER.log(Level.WARNING, "Claim with ID " + claimID + " does not exist.");
            return;
        }

        String newChecksum = SecurityUtils.hash(claim.getCowID() + claim.getInsurance() + claim.getIncidentType() +
                claim.getIncidentDate() + claim.getClaimDate() + claim.getUsername() + newStatus);

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setString(2, newChecksum);
            preparedStatement.setInt(3, claimID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Claim status updated successfully.");
            } else {
                System.out.println("No claim found with the specified ID.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
