package ui;

import javax.swing.*;
import auth.AuthService;
import database.Database;
import models.User;
import models.UserSession;

public class LoginForm extends JFrame {

    private JPanel LoginPanel;
    private JTextField fillEmail;
    private JPasswordField fillPassword;
    private JButton loginBn;
    private JButton goToSignupBtn;  // If you want a Signup redirect button

    private AuthService auth;

    public LoginForm() {

        setTitle("Login Form");
        setContentPane(LoginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        auth = new AuthService(new Database());

        loginBn.addActionListener(e -> login());

        if (goToSignupBtn != null) {
            goToSignupBtn.addActionListener(e -> {
                dispose();
                new SignupForm();
            });
        }

        setVisible(true);
    }

    private void login() {
        String email = fillEmail.getText().trim();
        String password = new String(fillPassword.getPassword()).trim();

        // Basic validation
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        User user = auth.login(email, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid email or password");
            return;
        }

        // Save the logged-in user's ID
        UserSession.setLoggedInUserId(user.getUserId());

        JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.getUsername());

        dispose();

        
    }
}
