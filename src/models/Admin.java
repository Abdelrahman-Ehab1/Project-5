package models;

public class Admin extends User {


    public Admin(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "Admin");
    }

    public Admin(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Admin", true);
    }


}
