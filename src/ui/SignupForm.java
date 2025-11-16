package ui;

import javax.swing.*;
import auth.AuthService;
import database.Database;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        auth = new AuthService(new Database());



        signupBn.addActionListener(e -> handleSignup());
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
