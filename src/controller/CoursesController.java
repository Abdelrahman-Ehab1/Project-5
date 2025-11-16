package controller;

import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Student;

import java.util.ArrayList;
import java.util.List;

public class CoursesController {
    private CoursesDatabase coursesDB;
    private Database usersDB;
    public CoursesController(CoursesDatabase coursesDB , Database usersDB) {
        this.coursesDB = coursesDB;
        this.usersDB = usersDB;
    }

    public boolean updateCourse(Course updatedCourse) {
        for (int i = 0; i <coursesDB.getAllCourses().size(); i++) {
            if (coursesDB.getAllCourses().get(i).getCourseId().equals(updatedCourse.getCourseId())) {
                coursesDB.getAllCourses().set(i, updatedCourse);
                coursesDB.saveCourses();
                return true;
            }
        }
        return false;
    }


    public boolean deleteCourse(String courseId) {
        boolean removed = coursesDB.getAllCourses().removeIf(c -> c.getCourseId().equals(courseId));
        if (removed) {
            coursesDB.saveCourses();
        }
        return removed;
    }

    public List<Course> getCoursesByInstructor(String instructorId) {      // Get courses by instructor
        List<Course> instructorCourses = new ArrayList<>();
        for (Course course : coursesDB.getAllCourses()) {
            if (course.getInstructorId().equals(instructorId)) {
                instructorCourses.add(course);
            }
        }
        return instructorCourses;
    }

//    public boolean courseExists(String courseId) {
//        return coursesDB.getCourseById(courseId) != null;
//    }


    void addStudentToCourse(String studentId ,String courseId){
        Course wantedCourse = coursesDB.getCourseById(courseId);
        if(wantedCourse.getStudentIds().contains(studentId))
            throw new IllegalArgumentException("student already enrolled in that course");

        wantedCourse.getStudentIds().add(studentId);
        Student student = (Student) usersDB.findById(studentId);
        student.getEnrolledCourses().add(wantedCourse);

        coursesDB.saveCourses();
        usersDB.saveUsers();
    }

    void removeStudentFromCourse(String studentId ,String courseId){
        Course wantedCourse = coursesDB.getCourseById(courseId);
        wantedCourse.getStudentIds().remove(studentId);           //that student removed from that course

        Student student = (Student) usersDB.findById(studentId);
        student.getEnrolledCourses().remove(wantedCourse);

        coursesDB.saveCourses();
        usersDB.saveUsers();
    }

    List<String> getEnrolledStudentIds(String courseId){
        Course wantedCourse = coursesDB.getCourseById(courseId);
        return wantedCourse.getStudentIds();
        //no need to save we're just viewing what's inside
    }

    void addLesson(String courseId, Lesson lesson)
    void updateLesson(String courseId, Lesson updatedLesson)
    void deleteLesson(String courseId, String lessonId)
    List<Lesson> getLessons(String courseId)


}

