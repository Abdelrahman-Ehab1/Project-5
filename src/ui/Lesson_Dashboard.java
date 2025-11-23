package ui;

import controller.CoursesController;
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

    Database Db = new Database();
    CoursesDatabase CD = new CoursesDatabase();
    CoursesController Con = new CoursesController(CD, Db);

    String instructorId = UserSession.getLoggedInUserId();
    List<Course> instructorCourses = Con.getCoursesByInstructor(instructorId);

    List<Lesson> lessonList;
    Course selectedCourse;
    String selectedCourseId;
    int selectedRow = -1;

    public Lesson_Dashboard() {

        setContentPane(LessonsPanel);
        setSize(900, 600);
        setLocationRelativeTo(null);


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

        // update by boda
        searchButton.addActionListener(e -> {

            selectedCourseId = CourseID_field.getText();
            selectedCourse = null;

            for (Course c : instructorCourses) {
                if (c.getCourseId().equalsIgnoreCase(selectedCourseId)) {
                    selectedCourse = c;
                    break;
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
