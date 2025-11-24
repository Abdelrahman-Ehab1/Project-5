package ui;

import controller.CertificateController;
import controller.CoursesController;
import controller.QuizController;
import controller.StudentController;
import database.CoursesDatabase;
import database.Database;
import models.Question;
import models.Quiz;

import javax.swing.*;
import java.lang.classfile.Label;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class QuizForm extends JFrame {
    private JPanel quizForm;
    private JButton submitButton;
    //private JButton submitButton;
    private JPanel questionContainerPanel;

    private QuizController quizControllerQues ; // ----->hereeeeeeeee
    private List<Question> questions;
    private boolean quizPassed = false;
    private List<Integer> studentAnswers = new ArrayList<>();
    private CertificateController certificateController;
    CoursesDatabase coursesDB = new CoursesDatabase();
    Database usersDB = new Database();
    CoursesController coursesController = new CoursesController(coursesDB, usersDB);
    StudentController controller = new StudentController(coursesController, coursesDB, usersDB);

    public QuizForm(String courseId , String lessonId , String currentUserId, QuizController quizController) {
        setContentPane(quizForm);
        setTitle("Take Quiz");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //quizControllerQues = new QuizController(lessonId)


        //this.quizControllerQues = new QuizController(lessonId);
        this.quizControllerQues = quizController;
        quizControllerQues.getQuiz().getAllQuestions().clear();
        quizControllerQues.setQuestionsOfQuiz();
        questions = quizControllerQues.getQuiz().getAllQuestions();

        //StudentController(CoursesController courseController, CoursesDatabase coursesDB, Database usersDB)
//        this.certificateController = new CertificateController(
//                quizControllerQues.getCoursesController(),
//                new StudentController(quizControllerQues.getCoursesController(),cour),
//                quizControllerQues.getUsersDB()
//        );
        this.certificateController = new CertificateController(
                quizControllerQues.getCoursesController(),
                quizControllerQues.getStudentController(),
                quizControllerQues.getUsersDB()   //Database usersDB = new Database();
        );
            List<ButtonGroup> choiceGroups = new ArrayList<>();

            JPanel questionsPanel = new JPanel();
            questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
            for (Question q : questions) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel qLabel = new JLabel(q.getTextQuestion());
                panel.add(qLabel);

                ButtonGroup group = new ButtonGroup();
                for (String option : q.getOptions()) {
                    JRadioButton rb = new JRadioButton(option);
                    group.add(rb);
                    panel.add(rb);
                }
                choiceGroups.add(group);
                questionsPanel.add(panel);
            }
            questionContainerPanel.add(questionsPanel);

            submitButton.addActionListener(e -> {

                studentAnswers.clear();// btshel ay old records
                for (ButtonGroup group : choiceGroups) {
                    int selectedIndex = -1;
                    int i = 0;
                    for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
                        AbstractButton button = buttons.nextElement();
                        if (button.isSelected()) {
                            selectedIndex = i;
                            break;
                        }
                        i++;
                    }
                    studentAnswers.add(selectedIndex); //-1 lw not selected
                }
                quizControllerQues.setStudentAnswer(studentAnswers);
                //Quiz quiz = new Quiz(lessonId);
                quizControllerQues.evaluateStudentQuiz(quizControllerQues.getQuiz().getAnswers());
                quizPassed = quizControllerQues.isPassed();
                //
//                quizController.setStudentAnswer((studentAnswers));
//                quizController.evaluateStudentQuiz(quizController.getQuiz().getAnswers());
//                quizPassed = quizController.isPassed();
                JPanel resultPanel = new JPanel();
                resultPanel.setLayout(new BoxLayout(resultPanel,BoxLayout.Y_AXIS));
                int lastscore = quizControllerQues.getQuizProgress().getScores()
                        .get(quizControllerQues.getQuizProgress().getScores().size()-1);
                JLabel scoreLabel = new JLabel("Score: "+ lastscore);
                JLabel statusLabel = new JLabel("passed: "+quizPassed);
                resultPanel.add(scoreLabel);

                resultPanel.add(statusLabel);

                List<Question> allQuestions = quizControllerQues.getQuiz().getAllQuestions();
                for(int i = 0 ; i < allQuestions.size() ; i++){
                    Question q = allQuestions.get(i);
                    int studentAns = studentAnswers.get(i);
                    int correctAns = q.getCorrectAnswer();

                    JLabel qLabel = new JLabel("Q" + (i+1) + ": "+ q.getTextQuestion());
                    JLabel studentLabel = new JLabel("Your answer: "+(studentAns>=0 ? q.getOptions().get(studentAns) : "Not answered"));
                    JLabel correctLabel = new JLabel ("correct answer: " + q.getOptions().get(correctAns));
                    resultPanel.add(qLabel);
                    resultPanel.add(studentLabel);
                    resultPanel.add(correctLabel);
                }
                questionContainerPanel.removeAll();
                questionContainerPanel.add(resultPanel);
                questionContainerPanel.revalidate();
                questionContainerPanel.repaint();
                //CertificateController certificateController = new CertificateController()
                //
                if (quizPassed) {
                    JOptionPane.showMessageDialog(this.quizForm, "Quiz is passed");
                    quizControllerQues.saveProgress(quizControllerQues.getStudent());
                    String realCourseId = quizControllerQues.getCourseId(lessonId);
                    certificateController.checkToGenerateCertificate(currentUserId, realCourseId);
                } else {
                    JOptionPane.showMessageDialog(this, "Quiz is failed");
                }
//            AccessLessonsForm accessLessonsForm = new AccessLessonsForm(courseId,currentUserId);
//            accessLessonsForm.setVisible(true);
//            dispose();  // bt2fl el quizform w tfth acesslessonform
            });
        }


    public boolean passedQuiz (){
        return quizPassed;
    }
}