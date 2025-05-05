package com.financetracker.view;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(int userId) {
        setTitle("Finance Tracker Dashboard");
        
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create tabbed pane with custom styling
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.PLAIN, 14));
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add tabs without icons
        tabs.addTab("Add Transaction", new AddTransactionPanel(userId));
        tabs.addTab("Transaction History", new TransactionHistoryPanel(userId));

        // Add padding around the main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(tabs, BorderLayout.CENTER);
        
        add(mainPanel);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
