package ui;

import javax.swing.*;
import auth.AuthService;
import database.Database;
import models.User;

public class LoginForm extends JFrame {

    // These fields MUST match the bindings inside LoginForm.form
    private JPanel LoginPanel;
    private JTextField fillEmail;
    private JPasswordField fillPassword;
    private JButton loginBn;

    private AuthService auth;

    public LoginForm() {

        setTitle("Login Form");
        setContentPane(LoginPanel);  // root panel from .form
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();                     // GUI Designer sets correct size
        setLocationRelativeTo(null);

        auth = new AuthService(new Database());

        loginBn.addActionListener(e -> login());
    }

    private void login() {
        String email = fillEmail.getText();
        String password = new String(fillPassword.getPassword());

        User user = auth.login(email, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid email or password");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.getUsername());

        dispose();
        new Dashboard(user.getUsername(), user.getRole());
    }
}
