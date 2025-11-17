package controller;

import database.CoursesDatabase;
import models.Course;
import models.Lesson;

import java.util.List;

public class CoursesController {

    private CoursesDatabase coursesDB;

    public CoursesController(CoursesDatabase coursesDB) {
        this.coursesDB = coursesDB;
    }

    // -------------------------
    //  Course CRUD
    // -------------------------
    public void createCourse(String title, String description, String instructorId) {
        int num = coursesDB.getAllCourses().size();
        String courseId = "C" + num;

        // Fix: While ID exists, increment
        while (coursesDB.getCourseById(courseId) != null) {
            num++;
            courseId = "C" + num;
        }

        Course newCourse = new Course(courseId, title, description, instructorId);
        coursesDB.addCourse(newCourse);
        coursesDB.saveCourses();
    }

    public boolean updateCourse(Course updated) {
        Course existing = coursesDB.getCourseById(updated.getCourseId());
        if (existing == null) return false;

        coursesDB.replaceCourse(updated);
        coursesDB.saveCourses();
        return true;
    }

    public boolean deleteCourse(String courseId) {
        Course existing = coursesDB.getCourseById(courseId);
        if (existing == null) return false;

        coursesDB.deleteCourse(courseId);
        coursesDB.saveCourses();
        return true;
    }


    // -------------------------
    // Enrollment (PDF version)
    // -------------------------
    public void addStudentToCourse(String studentId, String courseId) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;

        if (!course.getStudentIds().contains(studentId)) {
            course.getStudentIds().add(studentId);
            coursesDB.saveCourses();
        }
    }

    public void removeStudentFromCourse(String studentId, String courseId) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;

        course.getStudentIds().remove(studentId);
        coursesDB.saveCourses();
    }

    public List<String> getEnrolledStudentIds(String courseId) {
        Course course = coursesDB.getCourseById(courseId);
        return (course != null) ? course.getStudentIds() : null;
    }


    // -------------------------
    // LESSON MANAGEMENT
    // -------------------------
    public void addLesson(String courseId, Lesson lesson) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;

        course.getLessons().add(lesson);
        coursesDB.saveCourses();
    }

    public void updateLesson(String courseId, Lesson updatedLesson) {
        Course course = coursesDB.getCourseById(courseId);
        if (course == null) return;

        List<Lesson> lessons = course.getLessons();
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(updatedLesson.getLessonId())) {
                lessons.set(i, updatedLesson);
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
        return (course != null) ? course.getLessons() : null;
    }
}
