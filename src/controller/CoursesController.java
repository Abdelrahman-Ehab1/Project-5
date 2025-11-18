package controller;

import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Student;
import models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class CoursesController {
    private CoursesDatabase coursesDB;
    private Database usersDB;             //we can remove it
    public CoursesController(CoursesDatabase coursesDB , Database usersDB) {
        this.coursesDB = coursesDB;
        this.usersDB = usersDB;      //we can remove it
    }

    public void createCourse(String title, String description, String instructorId){
        int num = coursesDB.getAllCourses().size();
        String courseId = "C"+"0"+num;
        while (coursesDB.getCourseById(courseId) == null){
            num++;
            courseId = "C"+"0"+num;
        }
        Course addedCourse = new Course(courseId,description,instructorId,title);
        coursesDB.addCourse(addedCourse);
        coursesDB.saveCourses();

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

    public void deleteCourse(String courseId) {
//        boolean removed = coursesDB.getAllCourses().removeIf(c -> c.getCourseId().equals(courseId));
//        if (removed) {
//            coursesDB.saveCourses();
//        }
//        return removed;
        coursesDB.DeleteCourse(courseId);
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

    Course getCourseById(String courseId){
        return coursesDB.getCourseById(courseId);
    }

    List<Course> getAllCourses(){
        return coursesDB.getAllCourses();
    }

//    public boolean courseExists(String courseId) {
//        return coursesDB.getCourseById(courseId) != null;
//    }


    void addStudentToCourse(String studentId ,String courseId){
        Course wantedCourse = coursesDB.getCourseById(courseId);
        if(wantedCourse.getStudentIds().contains(studentId))
            throw new IllegalArgumentException("student already enrolled in that course");

        wantedCourse.getStudentIds().add(studentId);
        coursesDB.saveCourses();

       /* Student student = (Student) usersDB.findById(studentId);
        student.getEnrolledCourses().add(wantedCourse);
        usersDB.saveUsers();*/   //if we will save in the user File also
    }

    void removeStudentFromCourse(String studentId ,String courseId){
        Course wantedCourse = coursesDB.getCourseById(courseId);
        wantedCourse.getStudentIds().remove(studentId);           //that student removed from that course
        coursesDB.saveCourses();

       /* Student student = (Student) usersDB.findById(studentId);
        student.getEnrolledCourses().remove(wantedCourse);
        usersDB.saveUsers(); */
    }

    List<String> getEnrolledStudentIds(String courseId){
        Course wantedCourse = coursesDB.getCourseById(courseId);
        return wantedCourse.getStudentIds();
        //no need to save we're just viewing what's inside
    }

    Student getStudentById(String studentId){
        return (Student) usersDB.findById(studentId);
    }

    public void addLesson(String courseId, Lesson lesson) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;
// we're supposed to click on the course then click add,so this case won't happen
        course.getLessons().add(lesson);
        coursesDB.saveCourses();
    }

    public void updateLesson(String courseId, Lesson updatedLesson) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;

        List<Lesson> lessons = course.getLessons();  // because course has a list of lesson objects not string ids
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(updatedLesson.getLessonId())) {
                lessons.set(i, updatedLesson);             //exchange the old lesson by the new updated one
                coursesDB.saveCourses();
                return;
            }
        }
    }

    public void deleteLesson(String courseId, String lessonId) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;
        course.getLessons().removeIf(l -> l.getLessonId().equals(lessonId));
        coursesDB.saveCourses();
    }

    public List<Lesson> getLessons(String courseId) {
        Course course = coursesDB.getCourseById(courseId);
        return course != null ? course.getLessons() : null;
    }

}

