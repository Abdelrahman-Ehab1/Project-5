package models;

import controller.CoursesController;
import database.CoursesDatabase;
import database.Database;

import java.util.List;

public class Lesson {

    private String lessonId;
    private String title;
    private String content;
    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
    }

    public String getLessonId() { return lessonId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }

    public void setLessonId(String lessonId) { this.lessonId = lessonId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}
