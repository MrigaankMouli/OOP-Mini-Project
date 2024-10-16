package oopminiproject.dbmanagement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.logging.Logger;
import java.util.logging.Level;

public class FarmerDB {
    private static final Logger LOGGER = Logger.getLogger(FarmerDB.class.getName());

    private static final String dbName = "farmers";

    public static void createFarmerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS farmers ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "username TEXT NOT NULL UNIQUE, "
                   + "fullName TEXT NOT NULL, "
                   + "farmAddress TEXT NOT NULL, "
                   + "hashedPassword TEXT NOT NULL);";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Farmer's table created / already exists");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static void insertFarmer(String username, String fullName, String farmAddress, String hashedPassword) {
        String sql = "INSERT INTO farmers(username, fullName, farmAddress, hashedPassword) VALUES(?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, fullName);
            preparedStatement.setString(3, farmAddress);
            preparedStatement.setString(4, hashedPassword);

            preparedStatement.executeUpdate();
            System.out.println("Data inserted to farmersDB");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static String fetchPasswordHash(String username) {
        String sql = "SELECT hashedPassword FROM farmers WHERE username = ?";

        String fetchedHash = "";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fetchedHash = resultSet.getString("hashedPassword");
                System.out.println("Fetched hash: " + fetchedHash);
            } else {
                System.out.println("Unable to fetch.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return fetchedHash;
    }

    //TODO: write a full name fetch method for cow ownership in DB.
    //TODO: generic the fetch methods
}
