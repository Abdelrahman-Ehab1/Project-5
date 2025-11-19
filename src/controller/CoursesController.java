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
    private CoursesDatabase coursesDB;
    private Database usersDB;             //we can remove it
    public CoursesController(CoursesDatabase coursesDB , Database usersDB) {
        this.coursesDB = coursesDB;
        this.usersDB = usersDB;      //we can remove it
    }

    public void createCourse(String title, String desc, String instructorId) {
        String id;
        int num = 1;

        do {
            id = "C" + num;
            num++;
        } while (coursesDB.getCourseById(id) != null);

        Course c = new Course(id, title, desc, instructorId);
        coursesDB.addCourse(c);
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
        User user = usersDB.findById(studentId); // returns User object

        if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            // convert User → Student
            Student student = new Student(user.getUsername(), user.getEmail(), user.getPasswordHash());
            // Copy userId if needed
            // student.setUserId(user.getUserId()); // optional if you have setter
            return student;
        } else {
            throw new IllegalArgumentException(
                    "User with ID " + studentId + " is not a Student (role: " + user.getRole() + ")"
            );
        }
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
//    public void createLesson(String title, String Content,String Course_ID){
//        int num = coursesDB.getCourseById(Course_ID).getLessons().size();
//        String Lesson_Id = "L"+"0"+num;
//        while (coursesDB.getCourseById(Lesson_Id) == null)
//        {
//            num++;
//            Lesson_Id = "L"+"0"+num;
//        }
//        Lesson addedCourse = new Lesson(Lesson_Id,title,Content);
//        coursesDB.getCourseById()
//        coursesDB.saveCourses();
//
//    }
public void createLesson(String title, String content, String courseId) {
    Course course = coursesDB.getCourseById(courseId);
    if (course == null) return;

    int num = course.getLessons().size();
    String lessonId = "L" + "0" + num;

    // ensure ID is unique
    boolean exists = true;
    while (exists) {
        exists = false;
        for (Lesson l : course.getLessons()) {
            if (l.getLessonId().equals(lessonId)) {
                exists = true;
                num++;
                lessonId = "L" + "0" + num;
                break;
            }
        }
    }

    Lesson newLesson = new Lesson(lessonId, title, content);
    course.getLessons().add(newLesson);
    coursesDB.saveCourses();
}


}

