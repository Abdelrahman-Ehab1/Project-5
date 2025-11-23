package ui;

import controller.CoursesController;
import database.CoursesDatabase;
import database.Database;
import models.Course;
import models.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class CourseMangment extends JFrame {
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

    Course found = null;
    int i, yu;
    String tempT, tempD, tempE;

    Database Db = new Database();
    CoursesDatabase CD = new CoursesDatabase();
    CoursesController Con = new CoursesController(CD, Db);
    String x = UserSession.getLoggedInUserId();
    List<Course> m = Con.getCoursesByInstructor(x);

    public CourseMangment() {
        setContentPane(Course_Mangment_panel);
        setSize(900, 600);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Instructor_Dashboard instructor = new Instructor_Dashboard();
                instructor.setVisible(true);
                dispose();
            }
        });

        String[] mk = {"Course ID", "Description", "InstructorID", "Title", "Status"};

        DefaultTableModel formatt = new DefaultTableModel(mk, 0);
        for (i = 0; i < m.size(); i++) {
            formatt.addRow(new Object[]{
                    m.get(i).getCourseId(),
                    m.get(i).getDescription(),
                    m.get(i).getInstructorId(),
                    m.get(i).getTitle(),
                    m.get(i).getApprovalStatus()
            });
        }
        Courses_Table.setModel(formatt);

        Save_but.addActionListener(e -> {
            tempT = Title_field.getText();
            tempD = Description_Field.getText();
            tempE = EditDelete_Combobox.getSelectedItem().toString();

            if (tempD.isEmpty() || tempT.isEmpty() || (EditDelete_Combobox.getSelectedIndex() == -1)) {
                JOptionPane.showMessageDialog(Course_Mangment_panel, "Please make Sure you haven't left anything blank");
                return;
            }

            if (tempE.equalsIgnoreCase("Edit")) {
                m.get(yu).setTitle(tempT);
                m.get(yu).setDescription(tempD);

                Con.updateCourse(m.get(yu));
            } else if (tempE.equalsIgnoreCase("Delete")) {
                Con.deleteCourse(m.get(yu).getCourseId());
                m.remove(yu);
            }
        });

        searchButton.addActionListener(e -> {
            found = null;
            yu = 0;
            String f = Course_id_Searchh.getText();

            for (i = 0; i < m.size(); i++) {
                if (m.get(i).getCourseId().equalsIgnoreCase(f)) {
                    found = m.get(i);
                    yu = i;
                    break;
                }
            }

            if (found == null) {
                JOptionPane.showMessageDialog(Course_Mangment_panel, "Please Make Sure you have Entered a valid Course ID");
            } else {
                Title_field.setText(found.getTitle());
                Description_Field.setText(found.getDescription());
            }
        });

        Exit_but.addActionListener(e -> {
            setVisible(false);
            new Instructor_Dashboard();
        });

        NewsaveButton.addActionListener(e -> {
            if (!NewTitle_Field.getText().isEmpty() && !NewDescription_field.getText().isEmpty()) {
                Con.createCourse(NewTitle_Field.getText(), NewDescription_field.getText(), x);
            } else {
                JOptionPane.showMessageDialog(Course_Mangment_panel, "Please Make sure you didnt leave the Fields Blank");
            }
        });

        NewexitButton.addActionListener(e -> {
            setVisible(false);
            new Instructor_Dashboard().setVisible(true);
        });

        Courses_Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        setVisible(true);
    }
}
