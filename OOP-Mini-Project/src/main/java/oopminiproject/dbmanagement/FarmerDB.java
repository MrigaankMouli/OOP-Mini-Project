package oopminiproject.dbmanagement;

import oopminiproject.Claim;
import oopminiproject.Farmer;
import oopminiproject.utility.SecurityUtils;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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

    public static Farmer getFarmer(String username) {
        String sql = "SELECT id, username, fullName, farmAddress FROM farmers WHERE username = ?";
        Farmer farmer = null;

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("fullName");
                String farmAddress = resultSet.getString("farmAddress");
                int id = resultSet.getInt("id");
                farmer = new Farmer(id, username, fullName, farmAddress);
                System.out.println("Farmer fetched: " + farmer);
            } else {
                System.out.println("No farmer found with the username: " + username);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return farmer;
    }


    //TODO: generic the fetchItem method also.
    private static String fetchItem(String item, String username) {
        String sql = "SELECT " + item + " FROM farmers WHERE username = ?";
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

    public static String fetchFullName(String username){
        if(!isUsernameTaken(username)){
            System.out.println("User does not exist");
            return " ";
        }
        String itemToFetch = "fullName";
        return fetchItem(itemToFetch, username);
    }

    private static @NotNull List<Farmer> getFarmerList(String sql, @NotNull Consumer<PreparedStatement> statementHandler) {
        List<Farmer> farmers = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            statementHandler.accept(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String fullName = resultSet.getString("fullName");
                String farmAddress = resultSet.getString("farmAddress");

                Farmer farmer = new Farmer(id, username, fullName, farmAddress);
                farmers.add(farmer);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return farmers;
    }

    public static List<Farmer> getAllFarmers() {
        String sql = "SELECT id, username, fullName, farmAddress FROM farmers;";
        return getFarmerList(sql, preparedStatement -> {});
    }
}
