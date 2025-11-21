package models;

import java.util.Random;

public class User {

    protected String userId;
    protected String username;
    protected String email;
    protected String passwordHash;
    protected String role;

    public User(String username, String email, String passwordHash, String role) {
        this.userId = generateUserId();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = normalizeRole(role);
    }

    public User() {}

    public User(String userId, String username, String email, String passwordHash, String role, boolean fromJson) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = normalizeRole(role);
    }

    protected String normalizeRole(String role) {
        if (role == null || role.trim().isEmpty())
            return "STUDENT";
        return role.trim().toUpperCase();
    }

    private String generateUserId() {
        Random rand = new Random();
        return String.valueOf(10000 + rand.nextInt(90000));
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }

    public void setRole(String role) {
        this.role = normalizeRole(role);
    }
}
