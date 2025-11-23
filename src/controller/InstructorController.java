package controller;

import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Lesson;
import models.QuizProgress;
import models.Student;

import java.util.List;

public class InstructorController {
    CoursesDatabase CD=new CoursesDatabase();
    Database DB=new Database();
    int i;
    float Counter,CounterAllEnrolled;
    boolean Flag=false;
    float Avg;
    int Total;
    public float LessonAvg(String LessonID,String CourseID)
    {
        Flag=false;
        Avg=0;
        Counter=0;
        Course CourrseF=CD.getCourseById(CourseID);
        List<String> ID_ss =CourrseF.getStudentIds();
        for(i=0;i<ID_ss.size();i++)
        {
            Student studentF=(Student)DB.findById(ID_ss.get(i));
            if( studentF.getQuizProgressForLesson(LessonID)!=null) {
                Avg += studentF.getQuizProgressForLesson(LessonID).getAvgScores();
                Counter++;
                Flag=true;
            }
        }
        if(!Flag)
            return 0;

        return (Avg/Counter);
    }
    public float LessonCompletion(String LessonID,String CourseID)
    {
        Flag=false;
        Avg=0;
        Counter=0;
        CounterAllEnrolled=0;
        Course CourrseF=CD.getCourseById(CourseID);
        List<String> ID_ss =CourrseF.getStudentIds();
        for(i=0;i<ID_ss.size();i++)
        {
            Student studentF=(Student)DB.findById(ID_ss.get(i));
            if( studentF.getQuizProgressForLesson(LessonID)!=null) {
                CounterAllEnrolled++;
                if(studentF.getQuizProgressForLesson(LessonID).isPassed())
                {
                    Counter++;
                     Flag=true;
                }
            }
        }
        if(!Flag)
            return 0;

        return (Counter/CounterAllEnrolled);
    }
    public float CourseCompletion(String CourseID) {
        Flag = false;
        Counter = 0;
        Course CourrseF = CD.getCourseById(CourseID);
        List<Lesson> lessons = CourrseF.getLessons();
        List<String> ID_ss = CourrseF.getStudentIds();
        for (i = 0; i < ID_ss.size(); i++) {
            Student studentF = (Student) DB.findById(ID_ss.get(i));
            Total = 0;
            for (i = 0; i < lessons.size(); i++) {
                Lesson lesson = lessons.get(i);
                String LessonID = lesson.getLessonId();
                if (studentF.getQuizProgressForLesson(LessonID) == null) {
                    break;
                }

                if (!studentF.getQuizProgressForLesson(LessonID).isPassed()) {
                    break;
                }
                Total++;
            }
            if (Total == lessons.size())
                Counter++;
        }
        if(Counter==0 || ID_ss.isEmpty())
            return 0;
        return Counter/(float) ID_ss.size();
    }
//    Course CourrseF=CD.getCourseById(CourseID);
//    List<Lesson> lessons=CourrseF.getLessons();
}
