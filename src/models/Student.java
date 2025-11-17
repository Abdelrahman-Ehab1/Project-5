package models;

import controller.CoursesController;

import java.util.ArrayList;

public class Student extends User {
   private ArrayList <Course> enrolledCourses; // courses that the student in
   private ArrayList <Lesson> progress;
   public Student(String username, String email, String passwordHash){
      super(username, email, passwordHash, "STUDENT");
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
