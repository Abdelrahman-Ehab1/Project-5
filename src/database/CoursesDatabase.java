package database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDatabase {
    private static final String COURSES_FILE = "courses.json";
    private List<Course> courses;
    private Gson gson;

    public CoursesDatabase() {
        this.gson = new Gson();
        loadCourses();
    }

    // getting courses from JSON file
    private void loadCourses() {
        try {
            File file = new File(COURSES_FILE);
            if (!file.exists()) {
                courses = new ArrayList<>();
                saveCourses();  // we Create empty File if a file didn't exist
                return;
            }

            FileReader reader = new FileReader(file);
            courses = gson.fromJson(reader, new TypeToken<List<Course>>(){}.getType());

            if (courses == null) {
                courses = new ArrayList<>();
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading courses: " + e.getMessage());
            courses = new ArrayList<>();
        }
    }

    public void saveCourses() {
        try (FileWriter writer = new FileWriter(COURSES_FILE)) {
            gson.toJson(courses, writer);
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }

    public boolean addCourse(Course course) {
        if (courses.stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
            return false; // already exists
        }

        courses.add(course);
        saveCourses();
        return true;
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Course getCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;             // we might throw an exception
    }

}