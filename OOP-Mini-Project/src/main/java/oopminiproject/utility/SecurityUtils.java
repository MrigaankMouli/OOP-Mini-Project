package oopminiproject.utility;

import oopminiproject.Cow;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityUtils {
    private static final Logger LOGGER = Logger.getLogger(SecurityUtils.class.getName());

    public static String hash(String string) {
        String hashedString = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            hashedString = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return hashedString;
    }

    public static String cowHasher(Cow cow) {
        return hash(cow.getBreed() + cow.getAge() + cow.getWeight() + cow.getInsurance() +
                    cow.getVaccinationStatus() + cow.getOwner());
    }
}
