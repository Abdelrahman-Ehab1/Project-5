package database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Course;
import models.Lesson;

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

    private void loadCourses() {
        try {
            File file = new File(COURSES_FILE);
            if (!file.exists()) {
                courses = new ArrayList<>();
                saveCourses();
                return;
            }

            FileReader reader = new FileReader(file);
            courses = gson.fromJson(reader, new TypeToken<List<Course>>(){}.getType());

            if (courses == null) courses = new ArrayList<>();

            // update by boda to check approval status always exists
            for (Course course : courses) {
                if (course.getApprovalStatus() == null || course.getApprovalStatus().isEmpty()) {
                    course.setApprovalStatus("PENDING");
                }
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

    public void addCourse(Course course) {
        if (courses.stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
            throw new UnsupportedOperationException("Course already exists");
        }

        courses.add(course);
        saveCourses();
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public Course getCourseById(String courseId) {
        return courses.stream()
                .filter(c -> c.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public void DeleteCourse(String Id) {
        courses.removeIf(course -> course.getCourseId().equals(Id));
        saveCourses();
    }

    private boolean lessonIdExists(String targetId) {
        for (Course c : courses) {
            for (Lesson l : c.getLessons()) {
                if (l.getLessonId().equals(targetId)) return true;
            }
        }
        return false;
    }

    public String generateGlobalLessonId() {
        int n = 0;
        while (true) {
            String id = "L" + String.format("%02d", n);
            if (!lessonIdExists(id)) return id;
            n++;
        }
    }
}
