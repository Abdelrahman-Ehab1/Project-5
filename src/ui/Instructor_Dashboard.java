package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructor_Dashboard extends JFrame {
    private JButton CourseMang_But;
    private JButton LessonMang_but;
    private JButton ViewEnroll_but;
    private JButton Exit_but;
    private JPanel Instructor_Main;

    public Instructor_Dashboard() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(Instructor_Main);
        setLayout(null);
        setLocationRelativeTo(null);
setSize(900,900);
        CourseMang_But.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                CourseMangment m=new CourseMangment();
                m.setVisible(true);
            }
        });
        LessonMang_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Lesson_Dashboard();
            }
        });
        ViewEnroll_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ViewStudents();
            }
        });
        Exit_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Mainwindow();
            }
        });
    }
}
