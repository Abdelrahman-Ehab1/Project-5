package models;

import java.util.ArrayList;
import java.util.List;

public class QuizProgress {
    private int attempts;
    private List<Integer> scores;
    private boolean passed;
    private boolean canRetry;

    public QuizProgress(){
        this.scores = new ArrayList<>();

    }

    public int getAttempts() {
        return attempts;
    }

//    public void setAttempts(int attempts) {
//        this.attempts = attempts;
//    }
    public  void addAttempt(){
        attempts++;
        //return canRetryQuiz();
    }

//    public boolean isCanRetry() {
//        return canRetry;
//    }

//    public void setCanRetry(boolean canRetry) {
//        this.canRetry = canRetry;
//    }
    public boolean canRetryQuiz(){
        if(passed || attempts > 3) { // if the student passed the quiz,he can't retake it // retry policy is to take quiz 3 times as a max
            canRetry = false;
        }
        else {
            canRetry = true;
        }
        return canRetry;

    }

    public boolean isPassed() {

        for (int score :scores){
            if(score >= 3){
                passed = true;
                break;
            }
        }
        return passed;
    }

//    public void setPassed(boolean passed) {
//        this.passed = passed;
//    }


    public List<Integer> getScores() {
        return scores;
    }

//    public void setScores(List<Integer> scores) {
//        this.scores = scores;
//    }
}
