package models;

public class Lesson {

    private String lessonId;
    private String title;
    private String content;
    private Quiz quiz;
    private float AvrgMark;

    public Lesson(String lessonId, String title, String content){
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.quiz = new Quiz(lessonId);
        this.AvrgMark=AvrgMark;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Quiz getQuiz(){ return quiz; }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
