package ui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public Dashboard(String username, String role) {
        setTitle(role + " Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + username + " (" + role + ")", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Student button
        JButton studentButton = new JButton("Student");
        studentButton.addActionListener(e -> {
            // Placeholder: No action yet
            JOptionPane.showMessageDialog(this, "Student button clicked!");
        });
        buttonPanel.add(studentButton);

        // Instructor button
        JButton instructorButton = new JButton("Instructor");
        instructorButton.addActionListener(e -> {
            // Placeholder: No action yet
            JOptionPane.showMessageDialog(this, "Instructor button clicked!");
        });
        buttonPanel.add(instructorButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
