package database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String USERS_FILE = "users.json";
    private List<User> users;
    private Gson gson;

    public Database() {
        gson = new Gson();
        loadUsers();
    }

    private void loadUsers() {
        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) {
                users = new ArrayList<>();
                saveUsers();
                return;
            }

            FileReader reader = new FileReader(file);
            users = gson.fromJson(reader, new TypeToken<List<User>>(){}.getType());

            if (users == null) users = new ArrayList<>();

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }

    public void saveUsers() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user) {
        User finalUser = user;
        if (users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(finalUser.getEmail()))) {
            return false;
        }

        // Ensure userId is unique
        User finalUser1 = user;
        while (users.stream().anyMatch(u -> u.getUserId().equals(finalUser1.getUserId()))) {
            user = new User(user.getUsername(), user.getEmail(), user.getPasswordHash(), user.getRole());
        }

        users.add(user);
        saveUsers();
        return true;
    }

    public User findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }


    public User findById(String userId) {
        return users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
