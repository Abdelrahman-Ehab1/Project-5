package ui;

import controller.CoursesController;
import controller.InstructorController;
import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class CourseMangment extends JFrame{
    private JPanel Course_Mangment_panel;
    private JTable Courses_Table;
    private JTextField Course_id_Searchh;
    private JTextField Title_field;
    private JTextField Description_Field;
    private JButton Save_but;
    private JButton Exit_but;
    private JComboBox EditDelete_Combobox;
    private JButton searchButton;
    private JTextField NewTitle_Field;
    private JTextField NewDescription_field;
    private JButton NewsaveButton;
    private JButton NewexitButton;
    Course found=null;
    int i,yu;
    String tempT,tempD,tempE;
    Course mm=new Course("1234","adad","59020","hhjj");
    Course ml=new Course("1235","dada","59020","jjhh");
    Database Db=new Database();
    CoursesDatabase CD=new CoursesDatabase();
    CoursesController Con=new CoursesController(CD,Db);
    String x= UserSession.getLoggedInUserId();
    List<Course> m=Con.getCoursesByInstructor(x);
    InstructorController IC=new InstructorController();
    public CourseMangment() {
        setContentPane(Course_Mangment_panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(900,900);

        String [] mk={"Course ID","Description","InstructorID","Title","Course Completion percantage"};
        DefaultTableModel formatt=new DefaultTableModel(mk,0);
        for(i=0;i<m.size();i++)
        {
            formatt.addRow(new Object[]{m.get(i).getCourseId(),m.get(i).getDescription(),m.get(i).getInstructorId(),m.get(i).getTitle(),100*IC.CourseCompletion(m.get(i).getCourseId())+"%"});
        }
        Courses_Table.setModel(formatt);
        Save_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempT=Title_field.getText();
tempD=Description_Field.getText();
tempE=EditDelete_Combobox.getSelectedItem().toString();
if(tempD.isEmpty()||tempT.isEmpty()||(EditDelete_Combobox.getSelectedIndex()==-1))
{
    JOptionPane.showMessageDialog(Course_Mangment_panel,"Please make Sure you haven't left anything blank");
    Db.saveUsers();
    return;

}
if(tempE.equalsIgnoreCase("Edit"))
{
    m.get(yu).setTitle(tempT);
    m.get(yu).setDescription(tempD);
    Con.updateCourse(m.get(yu));
    Db.saveUsers();
}
else if(tempE.equalsIgnoreCase("Delete"))
                {
                    Con.deleteCourse(m.get(yu).getCourseId());
                    m.remove(yu);
                    Con.removeCourseAsInstructor(m.get(yu).getCourseId());
                    //removeCourseAsInstructor
                    Db.saveUsers();

                }
            }

        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                found=null;
                yu=0;
                String f=Course_id_Searchh.getText();
                for(i=0;i<m.size();i++)
                {
                    if(m.get(i).getCourseId().equalsIgnoreCase(f))
                    {
                        found=m.get(i);
                        yu=i;
                        Db.saveUsers();
                        break;
                    }
                }
                if(found==null)
                    JOptionPane.showMessageDialog(Course_Mangment_panel,"Please Make Sure you have Entered a valid Course ID");
                    else
                    {
                        Title_field.setText(found.getTitle());
                    Description_Field.setText(found.getDescription());
                        Db.saveUsers();
                    }

            }
        });
        Exit_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Instructor_Dashboard();
                Db.saveUsers();
            }
        });
        NewsaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(NewTitle_Field.getText().isEmpty()) && !(NewDescription_field.getText().isEmpty()))
                {

                    Con.createCourse(NewTitle_Field.getText(),NewDescription_field.getText(),x);
                    Db.saveUsers();
                }
                else
                {
                    JOptionPane.showMessageDialog(Course_Mangment_panel,"Please Make sure you didnt leave the Fields Blank");
                    Db.saveUsers();
                }
            }
        });
        NewexitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Instructor_Dashboard l =new Instructor_Dashboard();
                l.setVisible(true);
            }
        });
        Courses_Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

}
