package ui;

import controller.CoursesController;
import controller.Mybar;
import controller.StudentController;
import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.UserSession;
import org.jfree.chart.ui.UIUtils;
import tutorial.BarExample1;
import ui.Instructor_Dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class ViewStudents extends JFrame {
    private JTextField Course_ID_Field;
    private JTable ViewStudentTable;
    private JButton exitButton;
    private JPanel ViewStudentPanel;
    private JButton searchButton;
    UserSession Us;
    int i;
    Boolean Flag=false;
    List<String> IDs_Students;
    public ViewStudents() {
        setVisible(true);
        setContentPane(ViewStudentPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
       setSize(900,900);
        String [] xk={"userId", "role", "username", "email"};
        DefaultTableModel model=new DefaultTableModel(xk,0);
        StudentController m=new StudentController();
        Database Db=new Database();
        CoursesDatabase CD=new CoursesDatabase();
        CoursesController CC=new CoursesController(CD,Db);
       String Inst_ID=Us.getLoggedInUserId();
       List<Course> mm=CC.getCoursesByInstructor(Inst_ID);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                        new Instructor_Dashboard();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flag=false;
                for(i=0;i<mm.size();i++)
                {
                    if(mm.get(i).getCourseId().equalsIgnoreCase(Course_ID_Field.getText()))
                    {
                      Flag=true;
                        Course Foundd=mm.get(i);
                        IDs_Students=Foundd.getStudentIds();
                        if(IDs_Students.isEmpty())
                            JOptionPane.showMessageDialog(ViewStudentPanel,"there is no students enrolled in this Course");
                        for(i=0;i<Foundd.getStudentIds().size();i++)
                        {
                            model.addRow(new Object[]{Db.findById(IDs_Students.get(i)).getUserId(),Db.findById(IDs_Students.get(i)).getRole(),Db.findById(IDs_Students.get(i)).getUsername(),Db.findById(IDs_Students.get(i)).getEmail()});
                        }
                        ViewStudentTable.setModel(model);
                        break;
                    }
                }
                if(!Flag)
                    JOptionPane.showMessageDialog(ViewStudentPanel,"Please enter a Valid SearchID that is Correct and Could viewed by the Instructor ");
            }
        });
        ViewStudentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Mybar NewBar=new Mybar("Student "+IDs_Students.get(ViewStudentTable.getSelectedRow())+" Chart");
                NewBar.pack();
                UIUtils.centerFrameOnScreen(NewBar);
                NewBar.setVisible(true);
            }
        });
    }
}
