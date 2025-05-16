package org.example.taskmanager11.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class Utils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static String passwordHash(String salt, String password) {
        try {
            // Bcrypt / Scrypt
            MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");

            String input = salt + ":" + password;
            byte[] hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

            int n = 100;
            while (n-- > 0) {
                hash = messageDigest.digest(hash);
            }

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
