package ui;

import controller.CoursesController;
import controller.StudentController;
import database.CoursesDatabase;
import database.Database;
import models.Student;
import models.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentForm extends JFrame {
//    private JButton button3;
//    private JButton button4;
    private JPanel studentPanel;
    private JButton unenrollButton;
    private JButton enrollButton;
    private JButton certificatesButton;
//    private Student currentStudent;
private String currentUserId = UserSession.getLoggedInUserId();
    CoursesDatabase coursesDB = new CoursesDatabase();
    Database usersDB = new Database();
    CoursesController coursesController = new CoursesController(coursesDB, usersDB);
    StudentController controller = new StudentController(coursesController, coursesDB, usersDB);

    public StudentForm(){

        Student student = controller.getStudentById(currentUserId);

        setContentPane(studentPanel);
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);

      unenrollButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            UnenrollForm unenrollForm = new UnenrollForm();
            unenrollForm.setVisible(true);
            dispose();
        }
      });

        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                EnrollForm enrollForm = new EnrollForm();
                enrollForm.setVisible(true);
                dispose();
            }
        });

        certificatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open certificates frame
                // You'll need to pass the current student object
                // This depends on how you manage the current user
                CertificateForm certFrame = new CertificateForm(student);
                certFrame.setVisible(true);
                dispose();
                // Note: We don't dispose() this frame - user can come back
            }
        });

//        private Student getCurrentStudent() {
//            return this.currentStudent;
//        }

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
                dispose();
            }
        });

    }
}
