package oopminiproject.dbmanagement;

import oopminiproject.utility.SecurityUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AdminDB {
    private static final Logger LOGGER = Logger.getLogger(AdminDB.class.getName());
    private static final String dbName = "admins";

    public static void createAdminTable() {
        String sql = "CREATE TABLE IF NOT EXISTS admins (" +
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
        String sql = "SELECT " + item + " FROM admins WHERE username = ?";
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

    public static @NotNull String fetchPasswordHash(String username) {
        String itemToFetch = "hashedPassword";
        return fetchItem(itemToFetch, username);
    }

    public static void registerDefaultAdmin() {
        String checkSql = "SELECT COUNT(*) FROM admins;";
        String insertSql = "INSERT INTO admins(username, hashedPassword) VALUES(?, ?);";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement checkStatement = connection.prepareStatement(checkSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                insertStatement.setString(1, "admin");
                insertStatement.setString(2, SecurityUtils.hash("admin"));

                insertStatement.executeUpdate();
                System.out.println("Default admin registered in adminDB.");
            } else {
                System.out.println("AdminDB already contains an admin. Skipping registration.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}