package controller;

import database.CoursesDatabase;
import models.Course;
import java.util.ArrayList;
import java.util.List;

public class CoursesController {
    private CoursesDatabase coursesDB;

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
}

