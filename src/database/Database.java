package database;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String USERS_FILE = "users.json";
    private List<User> users;
    private Gson gson;

    public Database() {
        gson = new GsonBuilder().setPrettyPrinting().create();
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

            List<JsonObject> rawList = gson.fromJson(reader, new TypeToken<List<JsonObject>>(){}.getType());
            users = new ArrayList<>();

            if (rawList != null) {
                for (JsonObject obj : rawList) {

                    String role = (obj.has("role") && !obj.get("role").isJsonNull())
                            ? obj.get("role").getAsString().trim().toUpperCase()
                            : "STUDENT";

                    switch (role) {
                        case "ADMIN":
                            users.add(gson.fromJson(obj, Admin.class));
                            break;
                        case "INSTRUCTOR":
                            users.add(gson.fromJson(obj, Instructor.class));
                            break;

                        case "STUDENT":
                        default:
                            users.add(gson.fromJson(obj, Student.class));
                            break;
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
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
        if (users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail())))
            return false;

        users.add(user);
        saveUsers();
        return true;
    }

    public User findByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    public User findById(String userId) {
        return users.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
    }

    public List<User> getAllUsers() { return new ArrayList<>(users); }
}
