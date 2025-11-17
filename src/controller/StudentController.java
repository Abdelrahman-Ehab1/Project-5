package controller;

import database.CoursesDatabase;
import models.Course;
import models.Student;

import java.util.ArrayList;

public class StudentController {
    private Student student ;
    private CoursesDatabase coursedb;
    private CoursesController courseCont;


    public ArrayList<Course> getallCourses(){
        ArrayList <Course> allCourses = (ArrayList<Course>) coursedb.getAllCourses();// load courses from nasser -> returns all the courses from the courses.json file
        ArrayList<Course> availableCourses = new ArrayList<>();
        for(int i = 0 ; i < allCourses.size() ; i++) {
            boolean isEnrolled = false;
            if (student.getEnrolledCourses().equals(allCourses.get(i))) {
                isEnrolled = true;
            }
            if (!isEnrolled) {
                availableCourses.add(allCourses.get(i));
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
