package oopminiproject.dbmanagement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AdminDB {
    private static final Logger LOGGER = Logger.getLogger(AdminDB.class.getName());
    private static final String dbName = "farmers"; 

    public static void createAdminTable() {
        String sql = "CREATE TABLE IF NOT EXISTS admin_users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "hashedPassword TEXT NOT NULL);";
        
        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Admin table created / already exists");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private static String fetchItem(String item, String username) {
        String sql = "SELECT " + item + " FROM admin_users WHERE username = ?";
        String fetchedItem = "";
        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fetchedItem = resultSet.getString(item);
                System.out.println("Fetched item: " + fetchedItem);
            } else {
                System.out.println("Unable to fetch.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return fetchedItem;
    }

    public static String fetchPasswordHash(String username) {
        String itemToFetch = "hashedPassword";
        return fetchItem(itemToFetch, username);
    }

    public static boolean isUsernameTaken(String username) {
        String itemToFetch = "username";
        return !fetchItem(itemToFetch, username).isEmpty();
    }   
}