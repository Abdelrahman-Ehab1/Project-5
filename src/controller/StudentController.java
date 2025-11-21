package controller;

import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Lesson;
import models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private Student student;
    private final CoursesDatabase coursesDB;
    private final Database usersDB;
    private final CoursesController courseController;

    public StudentController() {
        this.usersDB = new Database();
        this.coursesDB = new CoursesDatabase();
        this.courseController = new CoursesController(coursesDB, usersDB);
    }

    public StudentController(CoursesController courseController, CoursesDatabase coursesDB, Database usersDB) {
        this.courseController = courseController;
        this.coursesDB = coursesDB;
        this.usersDB = usersDB;
    }

    public void setCurrentStudent(String studentId) {
        this.student = courseController.getStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found: " + studentId);
        }
    }

    public ArrayList<Course> getAvailableCourses() {
        ArrayList<Course> available = new ArrayList<>();

        if (student == null) return available;

        ArrayList<Course> all = new ArrayList<>(coursesDB.getAllCourses());
        List<String> enrolled = student.getEnrolledCourseIds();

        for (Course c : all) {
            if (!enrolled.contains(c.getCourseId())) {
                available.add(c);
            }
        }
        return available;
    }

    public List<Course> getEnrolledCourses() {
        ArrayList<Course> list = new ArrayList<>();

        if (student == null) return list;

        for (String id : student.getEnrolledCourseIds()) {
            Course c = coursesDB.getCourseById(id);
            if (c != null) list.add(c);
        }

        return list;
    }

    public void enrollInCourse(String studentId, String courseId) {
        courseController.addStudentToCourse(studentId, courseId);
        this.student = courseController.getStudentById(studentId);
    }

    public void unenrollFromCourse(String studentId, String courseId) {
        courseController.removeStudentFromCourse(studentId, courseId);
        this.student = courseController.getStudentById(studentId);
    }

    public List<Lesson> getLessonsByStudentInCourse(String studentId, String courseId) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        if (!course.getStudentIds().contains(studentId)) {
            throw new IllegalArgumentException("Student " + studentId + " is not enrolled in course " + courseId);
        }
        return course.getLessons();
    }

    public Student getStudentById(String studentId) {
        return courseController.getStudentById(studentId);
    }
    public void addLessonProgress(String studentId, String lessonId, String courseId) {
        Student s = courseController.getStudentById(studentId);
        //s.addProgressLesson(lessonId);
        s.addProgressLesson(courseId,lessonId);
        usersDB.saveUsers();
    }
    public void removeLessonProgress(String studentId, String lessonId, String courseId) {
        //String uniqueId = courseId+"-"+lessonId;
        Student s = courseController.getStudentById(studentId);
        //s.getProgressLessonIds().remove(lessonId);
        s.getProgressByCourse().get(courseId).remove(lessonId);
        usersDB.saveUsers();
    }


}
