package models;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Course> enrolledCourses;
    private ArrayList<Lesson> progress;

    public Student(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "STUDENT");

        this.enrolledCourses = new ArrayList<>();
        this.progress = new ArrayList<>();
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public ArrayList<Lesson> getProgress() {
        return progress;
    }

    public void setProgress(ArrayList<Lesson> progress) {
        this.progress = progress;
    }
}
