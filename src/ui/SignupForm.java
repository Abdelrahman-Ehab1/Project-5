package ui;

import javax.swing.*;
import auth.AuthService;
import database.Database;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignupForm extends JFrame {

    private JPanel SignupPanel;
    private JTextField fillEmail;
    private JPasswordField fillPassword;
    private JTextField fillUserName;
    private JComboBox<String> fillRole;
    private JButton signupBn;

    private AuthService auth;

    public SignupForm() {
        setTitle("Signup Form");
        setContentPane(SignupPanel);
        setSize(900, 600);
        // pack();
        setLocationRelativeTo(null);

        auth = new AuthService(new Database());



        signupBn.addActionListener(e -> handleSignup());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Mainwindow window = new Mainwindow();
                window.setVisible(true);
                dispose();
            }
        });
    }

    private void handleSignup() {
        String username = fillUserName.getText().trim();
        String email = fillEmail.getText().trim();
        String password = new String(fillPassword.getPassword());
        String role = (String) fillRole.getSelectedItem();

        try {
            boolean success = auth.signup(username, email, password, role);

            if (!success) {
                JOptionPane.showMessageDialog(this, "Signup failed. Email already exists.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Signup successful! Please login.");
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            dispose();  // Close signup form


        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
