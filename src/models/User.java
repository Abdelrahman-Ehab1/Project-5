package models;

import java.util.Random;

public class User {
    private String userId;        // 5-digit unique ID
    private String username;
    private String email;
    private String passwordHash;
    private String role;

    public User(String username, String email, String passwordHash, String role) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username must be at least 3 characters");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email must be in the format: name@gmail.com");
        }
        if (!isValidPassword(passwordHash)) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (!isValidRole(role)) {
            throw new IllegalArgumentException("Role must be STUDENT or INSTRUCTOR");
        }

        this.userId = generateUserId();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role.trim().toUpperCase();
    }

    // --- Generate random 5-digit user ID ---
    private String generateUserId() {
        Random rand = new Random();
        int id = 10000 + rand.nextInt(90000); // 10000 - 99999
        return String.valueOf(id);
    }

    // --- Getters ---
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }

    // --- Validation methods ---
    public static boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 3;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String regex = "^[\\w.-]+@gmail\\.com$";
        return email.matches(regex);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidRole(String role) {
        if (role == null) return false;
        role = role.trim().toUpperCase();
        return role.equals("STUDENT") || role.equals("INSTRUCTOR");
    }
}
