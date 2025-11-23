package models;

public class Lesson {

    private String lessonId;
    private String title;
    private String content;
    private Quiz quiz;


    public Lesson(String lessonId, String title, String content) {
        setLessonId(lessonId);
        this.title = title;
        this.content = content;
        this.quiz = new Quiz(lessonId);
    }
    public Lesson(String lessonId){
        setLessonId(lessonId);
        this.quiz = new Quiz(lessonId);
        // we still need to check for title and content
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
        if(lessonId == null || lessonId.isEmpty())
            throw new IllegalArgumentException("lesson id can't be empty");
        this.lessonId = lessonId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}