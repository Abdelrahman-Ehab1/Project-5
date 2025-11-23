package controller;

import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Student;
import models.Lesson;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class CoursesController {

    private final CoursesDatabase coursesDB;
    private final Database usersDB;

    public CoursesController(CoursesDatabase coursesDB, Database usersDB) {
        this.coursesDB = coursesDB;
        this.usersDB = usersDB;
    }
    public CoursesController(){
        this.coursesDB = new CoursesDatabase();
        this.usersDB = new Database();
    }


    public void createCourse(String title, String desc, String instructorId) {
        int num = 1;
        String id;

        do {
            id = "C" + num;
            num++;
        } while (coursesDB.getCourseById(id) != null);

        Course course = new Course(id, title, desc, instructorId);
        course.setApprovalStatus("PENDING");
        coursesDB.addCourse(course);
        coursesDB.saveCourses();
    }


    public boolean updateCourse(Course updatedCourse) {
        List<Course> all = coursesDB.getAllCourses();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCourseId().equals(updatedCourse.getCourseId())) {

                all.set(i, updatedCourse);
                coursesDB.saveCourses();
                return true;
            }
        }
        return false;
    }

    public void deleteCourse(String courseId) {
        coursesDB.DeleteCourse(courseId);
        coursesDB.saveCourses();
    }

    public List<Course> getCoursesByInstructor(String instructorId) {
        List<Course> result = new ArrayList<>();

        for (Course c : coursesDB.getAllCourses()) {
            if (c.getInstructorId().equals(instructorId)) {
                result.add(c);
            }
        }
        return result;
    }

    public Course getCourseById(String courseId) {
        return coursesDB.getCourseById(courseId);
    }

    public List<Course> getAllCourses() {
        return coursesDB.getAllCourses();
    }

    public Student getStudentById(String studentId) {

        User user = usersDB.findById(studentId);

        if (user == null)
            return null;

        if (!(user instanceof Student))
            throw new IllegalArgumentException("User with ID " + studentId + " is not a Student!");

        return (Student) user;
    }


    public void addStudentToCourse(String studentId, String courseId) {

        Course course = coursesDB.getCourseById(courseId);
        if (course == null)
            throw new IllegalArgumentException("Course not found!");

        if (course.getStudentIds().contains(studentId))
            throw new IllegalArgumentException("Student already enrolled!");

        // Add student to course
        course.getStudentIds().add(studentId);
        coursesDB.saveCourses();

        // Add course to student
        Student student = getStudentById(studentId);
        if (student == null)
            throw new IllegalArgumentException("Student not found!");

        student.enrollInCourse(courseId);
        usersDB.saveUsers();
    }


    public void removeStudentFromCourse(String studentId, String courseId) {

        Course course = coursesDB.getCourseById(courseId);
        if (course == null)
            throw new IllegalArgumentException("Course not found!");

        course.getStudentIds().remove(studentId);
        coursesDB.saveCourses();

        Student student = getStudentById(studentId);
        if (student == null)
            throw new IllegalArgumentException("Student not found!");

        student.getEnrolledCourseIds().remove(courseId);
        usersDB.saveUsers();
    }

    public List<String> getEnrolledStudentIds(String courseId) {
        Course c = coursesDB.getCourseById(courseId);
        return c != null ? c.getStudentIds() : new ArrayList<>();
    }

    public void updateLesson(String courseId, Lesson updatedLesson) {
        Course c = coursesDB.getCourseById(courseId);
        if (c == null)
            throw new IllegalArgumentException("Course not found!");

        List<Lesson> lessons = c.getLessons();

        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(updatedLesson.getLessonId())) {

                lessons.set(i, updatedLesson);
                coursesDB.saveCourses();
                return;
            }
        }
    }

    public void deleteLesson(String courseId, String lessonId) {

        Course c = coursesDB.getCourseById(courseId);
        if (c == null)
            throw new IllegalArgumentException("Course not found!");

        c.getLessons().removeIf(l -> l.getLessonId().equals(lessonId));
        coursesDB.saveCourses();
    }

    public List<Lesson> getLessons(String courseId) {
        Course c = coursesDB.getCourseById(courseId);
        return c != null ? c.getLessons() : new ArrayList<>();
    }

    public Course getCourseByLessonId(String lessonId){
        List<Course> allCourses =  coursesDB.getAllCourses();
        for(Course course : allCourses){
            Lesson lesson = course.getLessonById(lessonId);
            if(lesson != null)
                return course;
        }
        throw new IllegalArgumentException("Course not found!");
    }

    //    public void createLesson(String title, String content, String courseId) {
//        Course course = coursesDB.getCourseById(courseId);
//        if (course == null) return;
//
//        int num = course.getLessons().size();
//        String lessonId = "L" + "0" + num;
//
//        // ensure ID is unique
//        boolean exists = true;
//        while (exists) {
//            exists = false;
//            for (Lesson l : course.getLessons()) {
//                if (l.getLessonId().equals(lessonId)) {
//                    exists = true;
//                    num++;
//                    lessonId = "L" + "0" + num;
//                    break;
//                }
//            }
//        }
//
//        Lesson newLesson = new Lesson(lessonId, title, content);
//        course.getLessons().add(newLesson);
//        coursesDB.saveCourses();
//    }
    public void createLesson(String title, String content, String courseId) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;
        String lessonId = coursesDB.generateGlobalLessonId();
        Lesson newLesson = new Lesson(lessonId, title, content);
        course.getLessons().add(newLesson);
        coursesDB.saveCourses();
    }

    public void removeCourseAsInstructor(String courseId){
        Course course = coursesDB.getCourseById(courseId);
        if (course == null)
            throw new IllegalArgumentException("Course not found!");

        List<String> listStd= getEnrolledStudentIds(courseId);
        for(int i = 0 ; i<getEnrolledStudentIds(courseId).size() ; i++){
            removeStudentFromCourse(listStd.get(i),courseId);
        }

        /*for(String studentId : getEnrolledStudentIds(courseId)){
            removeStudentFromCourse(studentId ,courseId);
        }
        */
        coursesDB.DeleteCourse(courseId);
        coursesDB.saveCourses();

    }
}