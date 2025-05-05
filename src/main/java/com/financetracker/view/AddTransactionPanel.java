package com.financetracker.view;

import com.financetracker.controller.TransactionController;
import com.financetracker.models.Transaction;  // Changed from model to models

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;

public class AddTransactionPanel extends JPanel {
    private JComboBox<String> typeBox = new JComboBox<>(new String[]{"Income", "Expense"});
    private JTextField categoryField = new JTextField(10);
    private JTextField amountField = new JTextField(10);
    private JTextField descriptionField = new JTextField(15);
    private JButton addButton = new JButton("Add Transaction");
    private int userId;

    public AddTransactionPanel(int userId) {
        this.userId = userId;
        setLayout(new GridLayout(5, 2, 5, 5));
        add(new JLabel("Type:"));
        add(typeBox);
        add(new JLabel("Category:"));
        add(categoryField);
        add(new JLabel("Amount:"));
        add(amountField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel(""));
        add(addButton);

        addButton.addActionListener(e -> addTransaction());
    }

    private void addTransaction() {
        try {
            String type = (String) typeBox.getSelectedItem();
            String category = categoryField.getText();
            BigDecimal amount = new BigDecimal(amountField.getText());
            String description = descriptionField.getText();
            
            Transaction transaction = new Transaction(
                userId,
                type,
                category,
                amount,
                description,
                new Date()
            );
            
            TransactionController controller = new TransactionController();
            if (controller.addTransaction(transaction)) {
                JOptionPane.showMessageDialog(this, "Transaction added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error adding transaction", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your values.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        typeBox.setSelectedIndex(0);
        categoryField.setText("");
        amountField.setText("");
        descriptionField.setText("");
    }
}
