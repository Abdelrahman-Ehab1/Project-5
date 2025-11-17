package ui;

import controller.StudentController;
import models.Course;
import models.Student;
import models.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EnrollForm extends JFrame {
    private JPanel EnrollForm;
    private JTable table1;
    private JButton enrollbutton;
    private JScrollPane scroller;

    String currentUserId = UserSession.getLoggedInUserId();

    public EnrollForm() {
        setContentPane(EnrollForm);
        setTitle("Unenroll Student");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"CourseId", "Title", "Description", "InstructorId"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1.setModel(model);
        loadStudentsToTable(model);
        setVisible(true);


    }
    private void loadStudentsToTable(DefaultTableModel model) {
        StudentController controller = new StudentController();
        Student student = controller.getStudentbyId(currentUserId); // take student id from boda

        ArrayList<Course> courses = student.getEnrolledCourses();

        for (int i = 0; i < courses.size(); i++) {
            model.addRow(new Object[]{
                    courses.get(i).getCourseId(), courses.get(i).getTitle(),
                    courses.get(i).getDescription(), courses.get(i).getInstructorId()
            });
        }
    }

}