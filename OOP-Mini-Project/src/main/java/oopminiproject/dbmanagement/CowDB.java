package oopminiproject.dbmanagement;

import oopminiproject.Cow;
import oopminiproject.Session;
import oopminiproject.utility.SecurityUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CowDB {

    private static final Logger LOGGER = Logger.getLogger(CowDB.class.getName());

    private static final String dbName = "cows";

    public static void createCowTable() {
        String sql = "CREATE TABLE IF NOT EXISTS cows ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "breed TEXT NOT NULL, "
                   + "age INT CHECK (age >= 0 AND age <= 50), "
                   + "weight INT CHECK (weight >= 0), "
                   + "insurance TEXT NOT NULL, "
                   + "vaccinationStatus TEXT NOT NULL, "
                   + "owner TEXT NOT NULL, "
                   + "checksum TEXT NOT NULL);";

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Cow's table created / already exists");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public static void insertCow(String breed, int age, int weight, String insurance, String vaccinationStatus, String owner) {
        String sql = "INSERT INTO cows(breed, age, weight, insurance, vaccinationStatus, owner, checksum) VALUES(?, ?, ?, ?, ?, ?, ?);";

        String checksum = SecurityUtils.hash(breed + age + weight + insurance +
                vaccinationStatus + owner);

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, breed);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, weight);
            preparedStatement.setString(4, insurance);
            preparedStatement.setString(5, vaccinationStatus);
            preparedStatement.setString(6, owner);
            preparedStatement.setString(7, checksum);

            preparedStatement.executeUpdate();
            System.out.println("Data inserted to CowsDB");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @org.jetbrains.annotations.NotNull
    public static List<Cow> getOwnedCows() {
        String owner = Session.getInstance().getUsername();

        String sql = "SELECT id, breed, age, weight, insurance, vaccinationStatus, checksum FROM cows WHERE owner = ?";

        List<Cow> ownedCows = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connectToDatabase(dbName);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, owner);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String breed = resultSet.getString("breed");
                int age = resultSet.getInt("age");
                int weight = resultSet.getInt("weight");
                String insurance = resultSet.getString("insurance");
                String vaccinationStatus = resultSet.getString("vaccinationStatus");

                String calculatedHash = SecurityUtils.hash(breed + age + weight + insurance +
                                                            vaccinationStatus + owner);
                if (calculatedHash.equals(resultSet.getString("checksum"))) {
                    Cow cow = new Cow(id, breed, age, weight, insurance, vaccinationStatus, owner);
                    ownedCows.add(cow);
                } //TODO: what to do if this is untrue?
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return ownedCows;
    }
}
