package ui;


import controller.CoursesController;
import controller.InstructorController;
import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Lesson;
import models.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Lesson_Dashboard extends JFrame {
    private JTextField CourseID_field;
    private JButton searchButton;
    private JTable LessonsTable;
    private JTextField Title_field;
    private JTextField Content_field;
    private JButton NewsaveButton;
    private JButton deleteButton;
    private JButton editButton;
    private JTextField NewTitle;
    private JTextField NewContent;
    private JButton exitButton;
    private JPanel LessonsPanel;
    Database Db=new Database();
    List<Lesson> kk;
    CoursesDatabase CD=new CoursesDatabase();
    CoursesController Con=new CoursesController(CD,Db);
    String x= UserSession.getLoggedInUserId();
    List<Course> m=Con.getCoursesByInstructor(x);
    int i,xm=-1;
    Course LP;
    String IDD;
    InstructorController IC=new InstructorController();
    Boolean Flag=false;
    public Lesson_Dashboard() {
        setContentPane(LessonsPanel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,900);
        setLocationRelativeTo(null);
        String [] mk={"Lesson ID","Title","Content","Quiz AvrMark","Completion Percentage"};
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Instructor_Dashboard();

            }
        });
        NewsaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!NewTitle.getText().isEmpty()&&!NewContent.getText().isEmpty())
                    Con.createLesson(NewTitle.getText(),NewContent.getText(),IDD);
                else
                    JOptionPane.showMessageDialog(LessonsPanel,"Dont leave Blank");
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Title_field.getText().isEmpty()||Title_field.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(LessonsPanel,"Make sure there is no Blank");
                    return;
                }
                Lesson c=kk.get(xm);
                c.setTitle(Title_field.getText());
                c.setContent(Content_field.getText());
                Con.updateLesson(IDD,c);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(LessonsTable.getValueAt(xm,1).toString().isEmpty())
                {
                    JOptionPane.showMessageDialog(LessonsPanel,"Make sure that  is no Blank");
                    return;
                }
                Con.deleteLesson(IDD,LessonsTable.getValueAt(xm,0).toString()
                );
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flag=false;
                kk=null;
                LP=null;
                IDD=CourseID_field.getText();
                for(i=0;i<m.size();i++)
                {
                    if(IDD.equalsIgnoreCase(m.get(i).getCourseId()))
                    {
                        Flag=true;
                        LP=m.get(i);
                        break;
                    }
                }
                if(!Flag){
                    JOptionPane.showMessageDialog(LessonsPanel,"Please Enter a valid Course-ID that is accesible by Instructor ");
                    return;
                }

                DefaultTableModel formatt=new DefaultTableModel(mk,0);
                kk=LP.getLessons();
                if(kk.isEmpty())
                {
                    JOptionPane.showMessageDialog(LessonsPanel,"There is no lessons in this course");
                }
                for(i=0;i<kk.size();i++)
                {
                    formatt.addRow(new Object[]{kk.get(i).getLessonId(),kk.get(i).getTitle(),kk.get(i).getContent(),IC.LessonAvg(kk.get(i).getLessonId(),IDD),100*IC.LessonCompletion(kk.get(i).getLessonId(),IDD)+"%"});
                }
                LessonsTable.setModel(formatt);
            }
        });
        LessonsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xm=-1;
                xm =LessonsTable.getSelectedRow();
                Title_field.setText(LessonsTable.getValueAt(xm,1).toString());
                Content_field.setText(LessonsTable.getValueAt(xm,2).toString());
            }
        });
    }
}
