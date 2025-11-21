package controller;

import models.Quiz;
import models.QuizProgress;

public class QuizController {
    private Quiz quiz;
    private QuizProgress quizProgress;
    private String lessonId;

    public QuizController(String lessonId){
        setLessonId(lessonId);
        this.quiz = new Quiz(lessonId);
        this.quizProgress = new QuizProgress();

    }

    public void setLessonId(String lessonId) {
        if(lessonId == null || lessonId.isEmpty())
            throw new IllegalArgumentException("lesson id can't be empty");
        this.lessonId = lessonId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void addOption(String option){
        if(option == null || option.isEmpty())
            throw new IllegalArgumentException("option can not be empty");
        quiz.getoptions.add(option);
    }
}
