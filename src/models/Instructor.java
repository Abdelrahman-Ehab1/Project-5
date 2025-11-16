package models;

public class Instructor extends User {
    public Instructor(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "INSTRUCTOR");
    }
}