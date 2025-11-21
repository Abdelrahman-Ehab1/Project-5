package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StudentForm extends JFrame {
    private JButton button3;
    private JButton button4;
    private JPanel studentPanel;
    private JButton unenrollButton;
    private JButton enrollButton;


    public StudentForm(){
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
