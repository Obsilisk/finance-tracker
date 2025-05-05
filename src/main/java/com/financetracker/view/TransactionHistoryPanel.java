package com.financetracker.view;

import com.financetracker.controller.TransactionController;
import com.financetracker.models.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TransactionHistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private int userId;
    private TransactionController controller;

    public TransactionHistoryPanel(int userId) {
        this.userId = userId;
        this.controller = new TransactionController();
        setLayout(new BorderLayout());

        // Create table with columns
        String[] columns = {"Date", "Type", "Category", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTransactions());
        add(refreshButton, BorderLayout.SOUTH);

        // Load transactions
        refreshTransactions();
    }

    private void refreshTransactions() {
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Get transactions from database
        List<Transaction> transactions = controller.getTransactions(userId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Add transactions to table
        for (Transaction t : transactions) {
            Object[] row = {
                dateFormat.format(t.getDate()),
                t.getType(),
                t.getCategory(),
                t.getAmount(),
                t.getDescription()
            };
            tableModel.addRow(row);
        }
    }
}
