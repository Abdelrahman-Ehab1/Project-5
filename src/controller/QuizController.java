package controller;

import models.Question;
import models.Quiz;
import models.QuizProgress;

import java.util.Arrays;
import java.util.List;

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

    /*public void addOption(String option , int number){
        if(option == null || option.isEmpty())
            throw new IllegalArgumentException("option can not be empty");
        quiz.getQuestionByNumber(number).getOptions().add(option);
    }*/

    public boolean answerQuestion(int choice, int index){

        return choice == index;
    }

    public void addQuestion(String text , List<String> options ,int answerIndex ){
        Question question = new Question(text,options,answerIndex);
        quiz.addQuestion(question);
    }
    public void setQuestionsOfQuiz(){
        addQuestion("what is the capital of Egypt?", Arrays.asList("Alexandria","Cairo","Aswan","New York"), 1);
        addQuestion("what is the capital of France?", Arrays.asList("Paris","Cairo","Oslo","New York"), 0);
        addQuestion("what is the capital of Italy?", Arrays.asList("Alexandria","Cairo","Aswan","Rome"), 3);
        addQuestion("what is the capital of Germany?", Arrays.asList("Jeddah","Berlin","Aswan","New York"), 1);
        addQuestion("what is the capital of Japan?", Arrays.asList("Cairo","Istanbul","Tokyo","New York"), 2);
    };

    public void answerQuizQuestions(List<Integer> answers){
        quizProgress.addAttempt();
        int size = quiz.getAllQuestions().size();

        for(int i =0;i<size;i++){
            Question question = quiz.getAllQuestions().get(i);
            if(question.getCorrectAnswer() == answers.get(i))



        }
    }

}
