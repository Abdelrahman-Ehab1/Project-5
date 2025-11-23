package auth;

import database.Database;
import models.User;
import models.Student;
import models.Instructor;
import security.PasswordUtil;

public class AuthService {
    private Database database;

    public AuthService(Database database) {
        this.database = database;
    }
    public String hashPassword(String password) {
        return PasswordUtil.hashSHA256(password);
    }
    public boolean signup(String username, String email, String password, String role) {
        String hashed = hashPassword(password);

        User user;

        if (role != null) {
            role = role.trim().toUpperCase();
        }

        try {
            if ("STUDENT".equals(role)) {
                user = new Student(username, email, hashed);
            } else if ("INSTRUCTOR".equals(role)) {
                user = new Instructor(username, email, hashed);
                else if ("ADMIN".equals(role)) {
                    user = new Admin(username, email, hashed);
                }
            } else {
                throw new IllegalArgumentException("Role must be STUDENT or INSTRUCTOR");
            }
        } catch (IllegalArgumentException e) {
            throw e;
        }

        // Ensure userId is unique
        while (database.findById(user.getUserId()) != null) {
            if ("STUDENT".equals(role)) {
                user = new Student(username, email, hashed);
                else if ("ADMIN".equals(role)) {
                    user = new Admin(username, email, hashed);
                }
            } else {
                user = new Instructor(username, email, hashed);
            }
        }

        return database.addUser(user);
    }

    public User login(String email, String password) {
        String hashed = hashPassword(password);
        User user = database.findByEmail(email);

        if (user == null) {
            return null;
        }
        if (!user.getPasswordHash().equals(hashed)) {
            return null;
        }
        if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            return new Student(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPasswordHash()
            );
        }
        if ("INSTRUCTOR".equalsIgnoreCase(user.getRole())) {
            return new Instructor(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPasswordHash()
            );
        }
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return new Admin(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPasswordHash()
            );
        }
        return user;
    }
}
