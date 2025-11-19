package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentForm extends JFrame {
<<<<<<< Updated upstream
    private JPanel studentPanel;
=======
    private JButton button3;
    private JButton button4;
>>>>>>> Stashed changes
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


    }
}
