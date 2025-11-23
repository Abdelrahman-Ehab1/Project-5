package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainwindow extends JFrame {
    private JPanel MainWindow;
    private JButton loginBn;
    private JButton SignupBn;

    public Mainwindow() {
        setTitle("Welcome to Skill Forge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        //setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);   // Center the window


        setContentPane(MainWindow);
        loginBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
                dispose();
            }
        });
        SignupBn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupForm signupForm = new SignupForm();
                signupForm.setVisible(true);
                dispose();
            }
        });

    }
}
