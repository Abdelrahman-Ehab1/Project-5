package ui;

import ui.Instructor_Dashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewStudents extends JFrame {
    private JTextField Course_ID_Field;
    private JTable ViewStudentTable;
    private JButton exitButton;
    private JPanel ViewStudentPanel;
    private JButton searchButton;

    public ViewStudents() {
        setVisible(true);
        setContentPane(ViewStudentPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
       setSize(900,900);
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

            }
        });
    }
}
