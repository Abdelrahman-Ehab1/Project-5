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
    // private JButton goToSignupBtn;  // Optional signup redirect button

    private AuthService auth;

    public LoginForm() {
        setTitle("Login Form");
        setContentPane(LoginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        auth = new AuthService(new Database());

        loginBn.addActionListener(e -> login());

//        if (goToSignupBtn != null) {
//            goToSignupBtn.addActionListener(e -> {
//                dispose();
//                new SignupForm();
//            });
//        }

        setVisible(true);
    }

    private void login() {
        String email = fillEmail.getText().trim();
        String password = new String(fillPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        User user = auth.login(email, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid email or password");
            return;
        }

        // Save logged-in user ID
        UserSession.setLoggedInUserId(user.getUserId());

        JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.getUsername());

        dispose(); // Close login form
       // dispose();

        // Redirect based on role
        String role = user.getRole();
        if (role.equalsIgnoreCase("STUDENT")) {
            new StudentForm(); // Your student frame
        } else if (role.equalsIgnoreCase("INSTRUCTOR")) {
            new Instructor_Dashboard(); // Your instructor frame
        } else {
            JOptionPane.showMessageDialog(null, "Unknown role! Cannot redirect.");
        }
    }
}