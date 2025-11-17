package ui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public Lesson_Dashboard() {
        setContentPane(LessonsPanel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(900,900);
        setLocationRelativeTo(null);
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

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        LessonsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
