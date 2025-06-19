package com.example.assignment_writer.Login;
import java.security.MessageDigest;
public class HashString {
    public static String sha256(String input) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(input.getBytes());
                StringBuilder hexString = new StringBuilder();

                for (byte b : hash) {
                    hexString.append(String.format("%02x", b));
                }

                return hexString.toString();
            } catch (Exception e) {
                return null;
            }
        }
    }
