package ui;

import controller.StudentController;
import models.Course;
import models.Student;
import models.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EnrollForm extends JFrame {

    private JPanel enrollForm;
    private JTable table1;
    private JButton enrollbutton;
    private JScrollPane scroller;

    private String currentUserId = UserSession.getLoggedInUserId();

    private StudentController controller = new StudentController();

    public EnrollForm() {

        setContentPane(enrollForm);
        setTitle("Enroll in Course");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controller.setCurrentStudent(currentUserId);

        String[] columns = {"CourseId", "Title", "Description", "InstructorId"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1.setModel(model);

        loadAvailableCourses(model);

        enrollbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (table1.getSelectedRowCount() == 1) {

                    String courseId = (String) model.getValueAt(table1.getSelectedRow(), 0);

                    controller.enrollInCourse(currentUserId, courseId);

                    JOptionPane.showMessageDialog(EnrollForm.this,
                            "Enrolled successfully!");

                    model.removeRow(table1.getSelectedRow());
                }
                else if (table1.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(EnrollForm.this,
                            "No available courses to enroll.");
                }
                else {
                    JOptionPane.showMessageDialog(EnrollForm.this,
                            "Select a course first.");
                }

            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                StudentForm studentForm = new StudentForm();
                studentForm.setVisible(true);
                dispose();
            }
        });

    }

    private void loadAvailableCourses(DefaultTableModel model) {

        controller.setCurrentStudent(currentUserId);

        ArrayList<Course> courses = controller.getAvailableCourses();

        for (Course c : courses) {
            model.addRow(new Object[]{
                    c.getCourseId(),
                    c.getTitle(),
                    c.getDescription(),
                    c.getInstructorId()
            });
        }
    }
}
