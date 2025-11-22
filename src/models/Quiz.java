package models;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private List<Question> questions;
    //private List<Integer> answers;
    private String lessonId;    //might be unnessecary

    public Quiz(String lessonId) {
        setLessonId(lessonId);
        this.questions = new ArrayList<>();
        //this.answers = new ArrayList<>();
    }

    public List<Question> getAllQuestions() {
        return questions;
    }
//    public List

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        if(lessonId==null || lessonId.isEmpty())
            throw new IllegalArgumentException("lesson id can not be empty ");
        this.lessonId = lessonId;
    }

    public void addQuestion(Question question){
        if (question==null)
            throw new IllegalArgumentException("empty question added!");
        questions.add(question);
    }

//    public int getNumberOfQuestions(){
//        return questions.size();
//    }
//
//    public Question getQuestionByNumber(int number){
//        for(Question question : getAllQuestions()){
//            if(question.getNumber() == number){
//                return question;
//            }
//        }
//        throw new IllegalArgumentException("no question with this number");
//    }
}
