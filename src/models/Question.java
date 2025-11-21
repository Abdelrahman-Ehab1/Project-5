package models;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String textQuestion;
    private List<String> options;
    private int correctAnswer;
    private int number;
    // add mark attribute

    public Question(String textQuestion, int correctAnswer ) {

        setTextQuestion(textQuestion);
        setCorrectAnswer(correctAnswer);
        this.options = new ArrayList<>();
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        if(correctAnswer < 1 || correctAnswer > options.size())
            throw new IllegalArgumentException("choices out of range");

        this.correctAnswer = correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

//    public void setOptions(List<String> options) {
//        this.options = options;
//    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        if(textQuestion == null || textQuestion.isEmpty())
            throw new IllegalArgumentException("question can not be empty");

        this.textQuestion = textQuestion;
    }

    public int getNumber() {
        return number;
    }


    // the following code might get transferred to another class----
//    public void addOption(String option){
//        if(option == null || option.isEmpty())
//            throw new IllegalArgumentException("option can not be empty");
//        options.add(option);
//    }


}
