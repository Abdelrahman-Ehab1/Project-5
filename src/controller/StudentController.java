package controller;

import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Student;

import java.util.ArrayList;

public class StudentController {
    private Student student ;
    private CoursesDatabase coursedb;
    private CoursesController courseCont;

    public StudentController() {
        this.coursedb = new CoursesDatabase();
        Database usersDB = new Database();
        this.courseCont = new CoursesController(coursedb, usersDB);
    }

    public void setCurrentStudent(String studentId) {
        this.student = courseCont.getStudentById(studentId);
    }

    public ArrayList<Course> getallCourses() {

        ArrayList<Course> allCourses = (ArrayList<Course>) coursedb.getAllCourses();
        ArrayList<Course> availableCourses = new ArrayList<>();

        if (student == null) {
            System.out.println("ERROR: student is null — call setCurrentStudent() first.");
            return availableCourses;
        }

        for (int i = 0; i < allCourses.size(); i++) {
            Course course = allCourses.get(i);

            boolean isEnrolled = student.getEnrolledCourses().contains(course); // btcheck lw el student kan 3ndo el course da

            if (!isEnrolled) {
                availableCourses.add(course);
            }
        }

        return availableCourses;
    }

    public void unenrollfromCourse(String studentId, String courseId){
        courseCont.removeStudentFromCourse(studentId, courseId);
    }
    public void enrollInCourse(String studentId, String courseId){
        courseCont.addStudentToCourse(studentId, courseId);
    }
    public Student getStudentbyId(String StudentId){
       return courseCont.getStudentById(StudentId);
    }

}
