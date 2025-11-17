package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JTextField NewCourse_ID_Field;
    private JTextField NewTitle_Field;
    private JTextField NewDescription_field;
    private JButton NewsaveButton;
    private JButton NewexitButton;

    public CourseMangment() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(Course_Mangment_panel);
        setLayout(null);
        setLocationRelativeTo(null);
        setSize(900,900);

        Save_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        Exit_but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Instructor_Dashboard();
            }
        });
        NewsaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
