package ui;

import controller.CoursesController;
import controller.StudentController;
import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.Student;
import models.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class UnenrollForm extends JFrame {
    private JPanel unenrollForm;
    private JTable table1;
    private JButton unenrollbutton;
    private JScrollPane scroller;
    private JButton accessLessonButton;

    String currentUserId = UserSession.getLoggedInUserId();

    CoursesDatabase coursesDB = new CoursesDatabase();
    Database usersDB = new Database();
    CoursesController coursesController = new CoursesController(coursesDB, usersDB);
    StudentController controller = new StudentController(coursesController, coursesDB, usersDB);

    public UnenrollForm() {
        setContentPane(unenrollForm);
        setTitle("Unenroll Student");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controller.setCurrentStudent(currentUserId);

        String[] columns = {"CourseId", "Title", "Description", "InstructorId"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1.setModel(model);

        loadStudentsToTable(model);

        setVisible(true);

        unenrollbutton.addActionListener(e -> {
            if (table1.getSelectedRowCount() == 1) {

                int choice = JOptionPane.showConfirmDialog(
                        UnenrollForm.this,
                        "Final decision: you want to unenroll this course?",
                        "Confirm course unenroll",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }

                String courseId = (String) model.getValueAt(table1.getSelectedRow(), 0);

                controller.unenrollFromCourse(currentUserId, courseId);

                model.removeRow(table1.getSelectedRow());
            }

            else if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(UnenrollForm.this,
                        "Table is already empty, Can't view anything");
            }

            else if (table1.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(UnenrollForm.this,
                        "Select course first");
            }
        });


        accessLessonButton.addActionListener(e -> {
            if (table1.getSelectedRowCount() == 1) {

                int choice = JOptionPane.showConfirmDialog(
                        UnenrollForm.this,
                        "do you want to view lessons at this course?",
                        "Confirm accessing lessons",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }

                String courseId = (String) model.getValueAt(table1.getSelectedRow(), 0);

                AccessLessonsForm enrollForm = new AccessLessonsForm(courseId,currentUserId);
                enrollForm.setVisible(true);
                dispose();

            }

        });


        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                StudentForm studentForm = new StudentForm();
                studentForm.setVisible(true);
                dispose();
            }
        });
    }

    private void loadStudentsToTable(DefaultTableModel model) {

        controller.setCurrentStudent(currentUserId);

        Student student = controller.getStudentById(currentUserId);

        if (student == null) {
            JOptionPane.showMessageDialog(this,
                    "Error loading student data!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Course> courses = new ArrayList<>(controller.getEnrolledCourses());

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
