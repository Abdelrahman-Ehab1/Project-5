//package controller;
//
//public class InstructorController {
//
//
////    public int AverageMarkForLesson(String LessonID)
////    {
////
////    }
//}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controller;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Lesson;
import models.QuizProgress;
import models.Student;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.DataUtils;
import org.jfree.data.category.DefaultCategoryDataset;

public class Mybar extends ApplicationFrame {
    public Mybar(String title,String Id,String CourseID) {
        boolean Flag=false;
        int i;
        super(title);
        Database Db=new Database();
        CoursesDatabase Cd=new CoursesDatabase();
        CoursesController CC=new CoursesController(Cd,Db);
        Student student1=(Student)Db.findById(Id);
//       QuizController Qc=new QuizController(Id,"L03",CC,Db);
//       Qc.startQuiz();
//       Qc.setQuestionsOfQuiz();
//      Qc.setAnswersOfStudent(1);
//       Qc.setAnswersOfStudent(0);
//       Qc.setAnswersOfStudent(3);
//       Qc.setAnswersOfStudent(0);
//        Qc.setAnswersOfStudent(0);
//        Qc.getResult();
//        Qc.saveProgress(student1);
        Map<String,QuizProgress> QuizssProgress=student1.getQuizProgress();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Lesson> lessons=Cd.getCourseById(CourseID).getLessons();
        for(i=0;i<lessons.size();i++)
        {
            dataset.addValue(QuizssProgress.get(lessons.get(i).getLessonId())==null?(double)0 :(double)QuizssProgress.get(lessons.get(i).getLessonId()).getHighestScore(), Id,QuizssProgress.get(lessons.get(i).getLessonId())==null?"No Exam Lesson "+i:"Lesson "+i);
        }
//        dataset.addValue((double)QuizssProgress.get("L01").getScores().get(0), "Row 2", "Lesson 1");
//        dataset.addValue(QuizssProgress.get("L03")==null?(double)0 :(double)QuizssProgress.get("L03").getScores().get(0), "Row 2",QuizssProgress.get("L03")==null?"No Exam Lesson3":"Lesson 3");
        JFreeChart chart = ChartFactory.createBarChart(title, "Category", "Value", dataset);
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRange(0.0, 5.0);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane(chartPanel);
    }

}
