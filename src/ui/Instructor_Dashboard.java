package ui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Instructor_Dashboard extends JFrame {

    private JButton CourseMang_But;
    private JButton LessonMang_but;
    private JButton ViewEnroll_but;
    private JButton Exit_but;
    private JPanel Instructor_Main;

    public Instructor_Dashboard() {

        setContentPane(Instructor_Main);
        setTitle("Instructor Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);   // CENTER the window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        CourseMang_But.addActionListener(e -> {
            dispose();
            new CourseMangment().setVisible(true);
        });

        LessonMang_but.addActionListener(e -> {
            dispose();
            new Lesson_Dashboard().setVisible(true);
        });

        ViewEnroll_but.addActionListener(e -> {
            dispose();
            new ViewStudents().setVisible(true);
        });

        Exit_but.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new LoginForm().setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }
}
