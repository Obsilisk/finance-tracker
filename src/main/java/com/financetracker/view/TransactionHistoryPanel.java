package com.financetracker.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.financetracker.controller.TransactionController;
import com.financetracker.models.Transaction;

public class TransactionHistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private int userId;
    private TransactionController controller;
    private JLabel totalBalanceLabel;
    private JLabel totalIncomeLabel;
    private JLabel totalExpenseLabel;

    public TransactionHistoryPanel(int userId) {
        this.userId = userId;
        this.controller = new TransactionController();
        setLayout(new BorderLayout(10, 10));
        
        // Create summary panel
        JPanel summaryPanel = createSummaryPanel();
        add(summaryPanel, BorderLayout.NORTH);

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

        // Initial load of transactions
        refreshTransactions();
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Account Summary"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        totalIncomeLabel = new JLabel("Total Income: $0.00");
        totalExpenseLabel = new JLabel("Total Expenses: $0.00");
        totalBalanceLabel = new JLabel("Balance: $0.00");

        // Style labels
        Font summaryFont = new Font("Arial", Font.BOLD, 14);
        totalIncomeLabel.setFont(summaryFont);
        totalExpenseLabel.setFont(summaryFont);
        totalBalanceLabel.setFont(summaryFont);
        
        // Set colors
        totalIncomeLabel.setForeground(new Color(0, 150, 0));
        totalExpenseLabel.setForeground(new Color(150, 0, 0));
        totalBalanceLabel.setForeground(new Color(0, 0, 150));

        panel.add(totalIncomeLabel);
        panel.add(totalExpenseLabel);
        panel.add(totalBalanceLabel);

        return panel;
    }

    private void updateSummary(List<Transaction> transactions) {
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            if ("Income".equals(t.getType())) {
                totalIncome = totalIncome.add(t.getAmount());
            } else {
                totalExpense = totalExpense.add(t.getAmount());
            }
        }

        BigDecimal balance = totalIncome.subtract(totalExpense);

        totalIncomeLabel.setText(String.format("Total Income: $%.2f", totalIncome));
        totalExpenseLabel.setText(String.format("Total Expenses: $%.2f", totalExpense));
        totalBalanceLabel.setText(String.format("Balance: $%.2f", balance));
    }

    private void refreshTransactions() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = controller.getTransactions(userId);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Transaction t : transactions) {
            Object[] row = {
                dateFormat.format(t.getDate()),
                t.getType(),
                t.getCategory(),
                String.format("$%.2f", t.getAmount()),
                t.getDescription()
            };
            tableModel.addRow(row);
        }

        // Update the summary panel
        updateSummary(transactions);
    }
}
