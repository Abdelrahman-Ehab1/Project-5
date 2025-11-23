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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        setSize(900, 600);
        setLocationRelativeTo(null);
        String [] mk={"Lesson ID","Title","Content","Quiz AvrMark","Completion Percentage"};
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Instructor_Dashboard();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Instructor_Dashboard instructor = new Instructor_Dashboard();
                instructor.setVisible(true);
                dispose();
            }
        });
        String[] tableColumns = {"Lesson ID", "Title", "Content"};

        exitButton.addActionListener(e -> {
            setVisible(false);
            new Instructor_Dashboard();
        });

        // update by boda
        NewsaveButton.addActionListener(e -> {
            if (selectedCourse == null || !selectedCourse.isApproved()) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "This course is NOT APPROVED. You cannot add lessons.");
                return;
            }

            if (NewTitle.getText().isEmpty() || NewContent.getText().isEmpty()) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "Do not leave fields blank.");
                return;
            }

            Con.createLesson(NewTitle.getText(), NewContent.getText(), selectedCourseId);
            JOptionPane.showMessageDialog(LessonsPanel, "Lesson added.");
            refreshLessonsTable(tableColumns);
        });

        // update by boda
        //to prevent editing
        editButton.addActionListener(e -> {

            if (selectedCourse == null || !selectedCourse.isApproved()) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "This course is NOT APPROVED. You cannot edit lessons.");
                return;
            }

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "Select a lesson first.");
                return;
            }

            if (Title_field.getText().isEmpty() || Content_field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "Fields cannot be blank.");
                return;
            }

            Lesson lesson = lessonList.get(selectedRow);
            lesson.setTitle(Title_field.getText());
            lesson.setContent(Content_field.getText());

            Con.updateLesson(selectedCourseId, lesson);
            JOptionPane.showMessageDialog(LessonsPanel, "Lesson updated.");
            refreshLessonsTable(tableColumns);
        });

        // update by boda
        deleteButton.addActionListener(e -> {

            if (selectedCourse == null || !selectedCourse.isApproved()) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "This course is NOT APPROVED. You cannot delete lessons.");
                return;
            }

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "Select a lesson first.");
                return;
            }

            String lessonId = LessonsTable.getValueAt(selectedRow, 0).toString();
            Con.deleteLesson(selectedCourseId, lessonId);

            JOptionPane.showMessageDialog(LessonsPanel, "Lesson deleted.");
            refreshLessonsTable(tableColumns);
        });

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
            }

            if (selectedCourse == null) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "Invalid Course ID or not accessible.");
                return;
            }

            // check approval
            if (!selectedCourse.isApproved()) {
                JOptionPane.showMessageDialog(LessonsPanel,
                        "This course is NOT APPROVED. You cannot view lessons.");
                LessonsTable.setModel(new DefaultTableModel());
                return;
            }

            refreshLessonsTable(tableColumns);
        });

        LessonsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = LessonsTable.getSelectedRow();

                if (selectedRow >= 0) {
                    Title_field.setText(LessonsTable.getValueAt(selectedRow, 1).toString());
                    Content_field.setText(LessonsTable.getValueAt(selectedRow, 2).toString());
                }
            }
        });
        setVisible(true);
    }

    // update by boda
    private void refreshLessonsTable(String[] columns) {
        lessonList = selectedCourse.getLessons();

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Lesson lesson : lessonList) {
            model.addRow(new Object[]{
                    lesson.getLessonId(),
                    lesson.getTitle(),
                    lesson.getContent()
            });
        }

        LessonsTable.setModel(model);
    }
}
