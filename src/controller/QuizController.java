package controller;

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
    private QuizProgress quizProgress;
    private String lessonId;
    private CoursesController coursesController = new CoursesController();
    private List<Integer> answers;
    private Student student;
    private Database usersDB;

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

        this.answers = new ArrayList<>();
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

    public void setAnswersOfStudent(int choice){
        answers.add(choice);
    }

    public boolean isPassed(){
        return quizProgress.isPassed();
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

    public boolean canTakeQuiz(){
        return quizProgress.canRetryQuiz();   // called before clicking on retake quiz
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
        if (quizProgress.isPassed())
            student.addProgressLesson(getCourseId(lessonId), lessonId);

        answers.clear();
    }

    public void saveProgress(Student student) {

//        student.getQuizProgress().put(lessonId, quizProgress);
        student.saveQuizProgress(lessonId,quizProgress);     //student must be updated!!!
        usersDB.saveUsers();
        // then call studentController.updateStudent(student) in UI
    }

}
