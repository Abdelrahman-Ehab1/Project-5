package ui;

import javax.swing.*;
import auth.AuthService;
import database.Database;
import models.User;
import models.UserSession;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginForm extends JFrame {

    private JPanel LoginPanel;
    private JTextField fillEmail;
    private JPasswordField fillPassword;
    private JButton loginBn;

    private AuthService auth;

    public LoginForm() {
        setTitle("Login Form");
        setContentPane(LoginPanel);
        setSize(900, 600);
        //pack();
        setLocationRelativeTo(null);

        auth = new AuthService(new Database());

        loginBn.addActionListener(e -> login());

        setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Mainwindow window = new Mainwindow();
                window.setVisible(true);
                dispose();
            }
        });
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

        UserSession.setLoggedInUserId(user.getUserId());

        JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.getUsername());

        String role = user.getRole();
        if (role == null) {
            JOptionPane.showMessageDialog(this, "User role is missing in database!");
            return;
        }

        dispose();

        if (role.equalsIgnoreCase("STUDENT")) {
            StudentForm studentForm = new StudentForm();
            studentForm.setVisible(true);
            dispose();
        }
        else if (role.equalsIgnoreCase("INSTRUCTOR")) {
            Instructor_Dashboard instructorDashboard = new Instructor_Dashboard();
            instructorDashboard.setVisible(true);
            dispose();
        } else if (role.equalsIgnoreCase("ADMIN")) {
            AdminDashboard ad = new AdminDashboard();
            ad.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Unknown role! Cannot redirect.");
        }
    }
}
