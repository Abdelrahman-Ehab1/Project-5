package ui;

import controller.QuizController;
import models.Question;
import models.Quiz;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class QuizForm extends JFrame {
    private JPanel quizForm;
    private JButton submitButton;
    //private JButton submitButton;

    private QuizController quizController ; // ----->hereeeeeeeee
    private List<Question> questions;
    private boolean quizPassed = false;
    private List<Integer> studentAnswers;

    public QuizForm(String courseId , String lessonId , String currentUserId){
        this.quizController = new QuizController(lessonId);
        quizController.setQuestionsOfQuiz();
        questions = quizController.getQuiz().getAllQuestions();

        List<ButtonGroup> choiceGroups = new ArrayList<>();

        for(Question q :questions){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

            JLabel qLabel = new JLabel(q.getTextQuestion());
            panel.add(qLabel);

            ButtonGroup group = new ButtonGroup();
            for (String option : q.getOptions()){
                JRadioButton rb = new JRadioButton(option);
                group.add(rb);
                panel.add(rb);
            }
            choiceGroups.add(group);
            add(panel);
        }
        submitButton.addActionListener(e -> {

            studentAnswers.clear();// btshel ay old records
            for(ButtonGroup group: choiceGroups){
                int selectedIndex = -1;
                int i = 0;
                for(Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        selectedIndex = i;
                        break;
                    }
                    i++;
                }
                studentAnswers.add(selectedIndex); //-1 lw not selected
            }
            quizController.setStudentAnswer(studentAnswers);
            Quiz quiz = new Quiz(lessonId);
            quizController.evaluateStudentQuiz(quiz.getAnswers());
            quizPassed = quizController.isPassed();
            if(quizPassed){
                JOptionPane.showMessageDialog(this.quizForm, "Quiz is passed");
            }
            else{
                JOptionPane.showMessageDialog(this, "Quiz is failed");
            }

            AccessLessonsForm accessLessonsForm = new AccessLessonsForm(courseId,currentUserId);
            accessLessonsForm.setVisible(true);
            dispose();  // bt2fl el quizform w tfth acesslessonform
        });
    }
    public boolean passedQuiz (){
        return quizPassed;
    }
}