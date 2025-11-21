package models;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private List<Question> questions;
    private String lessonId;    //might be unnessecary
    public Quiz(String lessonId) {
        setLessonId(lessonId);
        this.questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        if(lessonId==null || lessonId.isEmpty())
            throw new IllegalArgumentException("lesson id can not be empty ");
        this.lessonId = lessonId;
    }

    public int getNumberOfQuestions(){
        return questions.size();
    }
}
