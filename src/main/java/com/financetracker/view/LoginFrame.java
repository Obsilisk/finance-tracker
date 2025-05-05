package com.financetracker.view;

import javax.swing.*;
import java.awt.*;
import com.financetracker.controller.UserController;
import com.financetracker.util.DatabaseConnection;  // Changed from database to util

public class LoginFrame extends JFrame {
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JButton loginButton = new JButton("Login");
    private JButton signupButton = new JButton("Sign Up");

    public LoginFrame() {
        setTitle("Personal Finance Tracker");

        // Test database connection
        if (!DatabaseConnection.testConnection()) {
            JOptionPane.showMessageDialog(this,
                "Database connection failed! Please check your database settings.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new GridLayout(3, 2, 5, 5));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(signupButton);

        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> handleSignup());

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void handleLogin() {
        UserController controller = new UserController();
        if (controller.authenticate(usernameField.getText(), new String(passwordField.getPassword()))) {
            int userId = controller.getUserId(usernameField.getText());
            new MainFrame(userId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    private void handleSignup() {
        if (usernameField.getText().trim().isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty!");
            return;
        }

        UserController controller = new UserController();
        try {
            if (controller.createUser(usernameField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(this, "User created successfully!");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "User creation failed! Username might already exist.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
