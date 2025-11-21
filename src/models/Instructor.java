package models;

public class Instructor extends User {

    public Instructor(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "INSTRUCTOR");
    }

    public Instructor(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "INSTRUCTOR", true);
    }

    public Instructor() {
        super();
        this.role = "INSTRUCTOR";
    }
}
