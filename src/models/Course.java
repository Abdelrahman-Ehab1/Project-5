package models;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private List<Lesson> lessons;
    private List<String> studentIds;
    private String approvalStatus = "PENDING";

    public Course(String courseId, String title, String description, String instructorId) {
        setCourseId(courseId);
        setTitle(title);
        setDescription(description);
        setInstructorId(instructorId);
        this.approvalStatus = "PENDING";
        this.lessons = new ArrayList<>();
        this.studentIds = new ArrayList<>();
    }
    // update by boda

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        if (approvalStatus == null)
            approvalStatus = "PENDING";
        this.approvalStatus = approvalStatus;
    }

    public boolean isApproved() {
        return "APPROVED".equalsIgnoreCase(approvalStatus);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        if (courseId == null || courseId.isEmpty())
            throw new IllegalArgumentException("Course Id cannot be empty");
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Course title cannot be empty");
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Course description cannot be empty");
        this.description = description;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        if (instructorId == null || instructorId.isEmpty())
            throw new IllegalArgumentException("Instructor Id cannot be empty");
        this.instructorId = instructorId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId))
                return lesson;
        }
        return null;
    }

    public List<String> getLessonsIds(){
        List<String> ids = new ArrayList<>();
        for(Lesson lesson : lessons){
            ids.add(lesson.getLessonId());
        }
        if(ids.isEmpty())
            throw new IllegalArgumentException("no lessons for this course");
        return ids;
    }
}
