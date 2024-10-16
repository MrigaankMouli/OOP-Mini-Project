package oopminiproject.utility;

import oopminiproject.controller.FarmerRegistrationController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordUtils {
    private static final Logger LOGGER = Logger.getLogger(FarmerRegistrationController.class.getName());

    public static String hashPassword(String password) {
        String hashedPassword = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            hashedPassword = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return hashedPassword;
    }
}
