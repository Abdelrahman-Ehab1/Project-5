package models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    //courseId, title, description, instructorId,
    //lessons[], students[].

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private List<Lesson> lessons;      //Composition relationship
    private List<String> studentIds;

    public Course(
            String courseId, String description, String instructorId, List<Lesson> lessons
            ,List<String> studentIds, String title) {

        setCourseId(courseId);
        setDescription(description);
        setInstructorId(instructorId);
        setTitle(title);
        this.lessons = new ArrayList<>();
        this.studentIds = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        if(courseId == null || courseId.isEmpty())     //uniqueness of course id will be handled in add course in db
            throw new IllegalArgumentException("Course Id can not be empty");
        else
            this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null || description.isEmpty())
            throw new IllegalArgumentException("Course Description can not be empty");
        else
            this.description = description;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        if(instructorId == null || instructorId.isEmpty())
            throw new IllegalArgumentException("Instructor's Id can not be empty");
        else
            this.instructorId = instructorId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

//    public void setLessons(List<Lesson> lessons) {
//        this.lessons = lessons;
//    }

    public List<String> getStudentIds() {
        return studentIds;
    }

//    public void setStudentIds(List<String> studentIds) {
//        this.studentIds = studentIds;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null || title.isEmpty())
            throw new IllegalArgumentException("Course title can not be empty");
        else
            this.title = title;
    }
}
