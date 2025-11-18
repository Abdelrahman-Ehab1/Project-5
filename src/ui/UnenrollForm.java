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

public class UnenrollForm extends JFrame{
    private JPanel unenrollForm;
    private JTable table1;
    private JButton unenrollbutton;
    private JScrollPane scroller;

    String currentUserId = UserSession.getLoggedInUserId();
    public UnenrollForm() {
        setContentPane(unenrollForm);
        setTitle("Unenroll Student");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"CourseId", "Title", "Description", "InstructorId"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1.setModel(model);
        loadStudentsToTable(model);
        setVisible(true);

        unenrollbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table1.getSelectedRowCount()==1){
                    int choice = JOptionPane.showConfirmDialog(UnenrollForm.this,"Final decision: you want to unenroll this course ?","Confirm course unenroll",JOptionPane.YES_NO_OPTION);

                    if(choice == 1 || choice==-1 ){ // -1 y3ny afal das 3la x  // 1 y3ny das 3la NO
                        return;
                    }

                    StudentController student = new StudentController();
                    String courseId = (String) model.getValueAt(table1.getSelectedRow(), 0);
                    student.unenrollfromCourse(currentUserId , courseId); //studentid from boda
                    model.removeRow(table1.getSelectedRow());

                }

                else if(model.getRowCount()==0){
                    JOptionPane.showMessageDialog(UnenrollForm.this, "Table is already empty, Can't view anything");
                }

                else if(table1.getSelectedRowCount()==0){  // btrag3 -1 lw mfesh row selected msh 0
                    JOptionPane.showMessageDialog(UnenrollForm.this,"Select course first");
                }


            }
        });


        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                StudentForm studentForm =  new StudentForm();
                studentForm.setVisible(true); // btrag3ny lel student view lw dost 3la x
                dispose();            // bt2fl el safha el ana fatha
            }
        });
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