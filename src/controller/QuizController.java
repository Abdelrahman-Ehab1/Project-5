package controller;

import models.Question;
import models.Quiz;
import models.QuizProgress;
import database.CoursesDatabase;
import database.Database;
import models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;

public class QuizController {
    private Quiz quiz;

    public QuizProgress getQuizProgress() {
        return quizProgress;
    }

    public void setQuizProgress(QuizProgress quizProgress) {
        this.quizProgress = quizProgress;
    }

    private QuizProgress quizProgress;
    private String lessonId;
    private CoursesController coursesController = new CoursesController();
    private List<Integer> answers;
    private Student student;
    private CoursesDatabase courseDB;
    private Database usersDB = new Database();
    int marks;
    private List<Integer> studentAnswer;
    String currentUserId = UserSession.getLoggedInUserId();
    private StudentController studentController = new StudentController(coursesController,courseDB , usersDB);

    public StudentController getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public QuizController(String lessonId){
        this.answers = new ArrayList<>();
        setLessonId(lessonId);
        this.quiz = new Quiz(lessonId);
        this.quizProgress = new QuizProgress();
        if(currentUserId == null || currentUserId.isEmpty())
            throw new IllegalArgumentException("student id can't be empty");

        /*this.usersDB = usersDB;
        this.coursesController = coursesController;*/
        Course course = coursesController.getCourseByLessonId(lessonId);
        Lesson lesson = course.getLessonById(lessonId);
        this.student = coursesController.getStudentById(currentUserId);
//        if (!student.getEnrolledCourseIds().contains(course.getCourseId())) {
//            throw new IllegalStateException("Student is not enrolled in this course.");
//        }


        this.quiz =lesson.getQuiz();
        this.quizProgress = student.getQuizProgressForLesson(lessonId);
        if (this.quizProgress == null)
            this.quizProgress = new QuizProgress();

        this.studentAnswer = new ArrayList<>();
    }

    public QuizController(String studentId, String lessonId ,CoursesController coursesController,Database usersDB){

        setLessonId(lessonId);
        if(studentId == null || studentId.isEmpty())
            throw new IllegalArgumentException("student id can't be empty");

        this.usersDB = usersDB;
        this.coursesController = coursesController;
        Course course = coursesController.getCourseByLessonId(lessonId);
        Lesson lesson = course.getLessonById(lessonId);
        this.student = coursesController.getStudentById(studentId);
//        if (!student.getEnrolledCourseIds().contains(course.getCourseId())) {
//            throw new IllegalStateException("Student is not enrolled in this course.");
//        }


        this.quiz =lesson.getQuiz();
        this.quizProgress = student.getQuizProgressForLesson(lessonId);
        if (this.quizProgress == null)
            this.quizProgress = new QuizProgress();

        this.studentAnswer = new ArrayList<>();
    }

    public Student getStudent() {
        return student;
    }

    public List<Integer> getStudentAnswer() {
        return studentAnswer;
    }
    public void setStudentAnswer(List<Integer> studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public void evaluateStudentQuiz(List<Integer> answers) {
        marks = 0;
        // quizProgress.addAttempt();
        int size = getStudentAnswer().size();
        for (int i = 0; i < size; i++) {
            if (studentAnswer.get(i) == answers.get(i)) { // 1 1 2 3 1   1, 0, 3, 1, 2
                marks++;
                // 4 question wrong  // will add logic to handle showing the ui of correct answer by using a new list to hold the false questions
            }
        }
        quizProgress.getScores().add(marks);
    }
    public boolean isPassed() {
        if (quizProgress.isPassed()) {
            return true;
        } else {
            return false;
        }

    }

    public CoursesController getCoursesController() {
        return coursesController;
    }
/*
    public StudentController getStudentController() {
        return studentController;
    }
*/
    public Database getUsersDB() {
        return usersDB;
    }


    public boolean canStartQuiz() {// this function will be called from ui

        if (quizProgress.getCanRetry()) {
            quizProgress.addAttempt();
            return true;
        } else {
            return false;
        }
    }
    public void setLessonId(String lessonId) {
        if(lessonId == null || lessonId.isEmpty())
            throw new IllegalArgumentException("lesson id can't be empty");
        this.lessonId = lessonId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getStudentId() {
        return student.getUserId();
    }


    /*public void addOption(String option , int number){
        if(option == null || option.isEmpty())
            throw new IllegalArgumentException("option can not be empty");
        quiz.getQuestionByNumber(number).getOptions().add(option);
    }*/

    public boolean answerQuestion(int choice, int index){

        return choice == index;
    }

    public void setAnswersOfStudent(int choice){
        answers.add(choice);
    }

//    public boolean isPassed(){
//        return quizProgress.isPassed();
//    }

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

    public boolean canTakeQuiz(){
        return quizProgress.getCanRetry();   // called before clicking on retake quiz
    }

    public void startQuiz() {
        if (!canTakeQuiz())
            throw new IllegalStateException("Max attempts reached or quiz already passed.");

        answers.clear();
    }

    public String getCourseId(String lessonId){
        return coursesController.getCourseByLessonId(lessonId).getCourseId();
    }


    public void getResult(){   // when student click on Submit
        if (!canTakeQuiz())
            return;

        quizProgress.addAttempt();

        int mark = 0;
        int size = quiz.getAllQuestions().size();

        if (answers.size() != size)
            throw new IllegalStateException("Student must answer all questions.");

        for (int i = 0; i < size; i++) {
            Question q = quiz.getAllQuestions().get(i);
            if (q.getCorrectAnswer() == answers.get(i))
                mark++;
        }

        quizProgress.addScore(mark);   // attempt added also,a mark added to the Scores list
        if (isPassed()) {
            student.addProgressLesson(getCourseId(lessonId), lessonId);

            String courseId = getCourseId(lessonId);
            Course course = coursesController.getCourseByLessonId(lessonId);

            boolean allPassed = true;
            for (Lesson l : course.getLessons()) {
                QuizProgress qp = student.getQuizProgressForLesson(l.getLessonId());
                if (qp == null || !qp.isPassed()) {
                    allPassed = false;
                    break;
                }
            }

            if (allPassed) {
                Certificate certificate = new Certificate(student.getUserId(), courseId);
                student.addCertificate(certificate);

                CertificateJsonGenerator generator = new CertificateJsonGenerator();
                generator.generateJsonCertificate(certificate);

                usersDB.saveUsers(); // persist into users.json
            }
        }
        answers.clear();
    }

    public void saveProgress(Student student) {

//        student.getQuizProgress().put(lessonId, quizProgress);
        student.saveQuizProgress(lessonId,quizProgress);     //student must be updated!!!
        usersDB.saveUsers();
        // then call studentController.updateStudent(student) in UI
    }

}