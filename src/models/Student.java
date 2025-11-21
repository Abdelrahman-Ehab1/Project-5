package models;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<String> enrolledCourseIds = new ArrayList<>();
    private List<String> progressLessonIds = new ArrayList<>();
    private QuizProgress quizProgress;

    public Student(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "STUDENT");
        this.quizProgress = new QuizProgress();
    }

    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "STUDENT", true);
        this.quizProgress = new QuizProgress();
    }

    public Student() {
        super();
        this.role = "STUDENT";
        this.quizProgress = new QuizProgress();
    }

    public List<String> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public void setEnrolledCourseIds(List<String> enrolledCourseIds) {
        this.enrolledCourseIds = enrolledCourseIds;
    }

    public boolean enrollInCourse(String courseId) {
        if (!enrolledCourseIds.contains(courseId)) {
            enrolledCourseIds.add(courseId);
            return true;
        }
        return false;
    }

    public List<String> getProgressLessonIds() {
        return progressLessonIds;
    }

    public void setProgressLessonIds(List<String> progressLessonIds) {
        this.progressLessonIds = progressLessonIds;
    }

    public boolean addProgressLesson(String lessonId) {
        if (!progressLessonIds.contains(lessonId)) {
            progressLessonIds.add(lessonId);
            return true;
        }
        return false;
    }
}