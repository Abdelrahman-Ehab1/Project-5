package ui;

import database.CoursesDatabase;
import models.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminDashboard extends JFrame {

    private JTable Courses_Table;
    private JButton approveBn;
    private JButton declineBn;
    private JPanel AdminForm;
    private JScrollPane scroll;

    private CoursesDatabase coursesDB;

    public AdminDashboard() {
        setVisible(true);
        setTitle("Admin Dashboard");
        setSize(900, 600);
        setContentPane(AdminForm);
        setLocationRelativeTo(null);

        coursesDB = new CoursesDatabase();

        setupTable();
        setupButtonActions();
        loadPendingCourses();



        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
                dispose();
            }
        });
    }

    private void setupTable() {
        Courses_Table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Course ID", "Title", "Instructor", "Status"}
        ));
    }

    private void loadPendingCourses() {
        DefaultTableModel model = (DefaultTableModel) Courses_Table.getModel();
        model.setRowCount(0);

        for (Course c : coursesDB.getAllCourses()) {
            if ("PENDING".equalsIgnoreCase(c.getApprovalStatus())) {
                model.addRow(new Object[]{
                        c.getCourseId(),
                        c.getTitle(),
                        c.getInstructorId(),
                        c.getApprovalStatus()
                });
            }
        }
    }

    private void setupButtonActions() {

        approveBn.addActionListener(e -> {
            int row = Courses_Table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course to approve.");
                return;
            }

            String courseId = Courses_Table.getValueAt(row, 0).toString();
            Course course = coursesDB.getCourseById(courseId);

            if (course == null) {
                JOptionPane.showMessageDialog(this, "Course not found!");
                return;
            }

            course.setApprovalStatus("APPROVED");
            coursesDB.saveCourses();

            JOptionPane.showMessageDialog(this, "Course approved successfully!");
            loadPendingCourses();
        });

        declineBn.addActionListener(e -> {
            int row = Courses_Table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course to decline.");
                return;
            }

            String courseId = Courses_Table.getValueAt(row, 0).toString();
            Course course = coursesDB.getCourseById(courseId);

            if (course == null) {
                JOptionPane.showMessageDialog(this, "Course not found!");
                return;
            }

            course.setApprovalStatus("REJECTED");
            coursesDB.saveCourses();

            JOptionPane.showMessageDialog(this, "Course declined.");
            loadPendingCourses();
        });
    }
}
