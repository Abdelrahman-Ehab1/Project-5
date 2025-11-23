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

    public  void addAttempt(){
        attempts++;
    }

    public boolean canRetryQuiz(){
        // if the student passed the quiz,he can't retake it // retry policy is to take quiz 3 times as a max
        if (passed) {
            canRetry = false;
        } else if (attempts >= 3) {
            canRetry = false;
        } else {
            canRetry = true;
        }
    }

    public boolean isPassed() {

        for (int score :scores){
            if(score >= 3){
                passed = true;
                return true;
            }
        }
        passed = false;
        return false;
    }

//    public void setPassed(boolean passed) {
//        this.passed = passed;
//    }


    public List<Integer> getScores() {
        return scores;
    }

    public void addScore(int score){
        scores.add(score);
        isPassed();
    }
    public float getAvgScores() {
        float Avg;

        Avg=0;
     for(int i=0;i<scores.size();i++)
        {
          Avg+=scores.get(i);
        }
Avg=Avg/scores.size();
return Avg;
    }
    public int getHighestScore() {
        int Max=0;
        for(int i=0;i<scores.size();i++)
        {
            if(Max<scores.get(i))
            Max=scores.get(i);
        }
        return Max;
    }
}