package com.financetracker.models;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private int id;
    private int userId;
    private String type;
    private String category;
    private BigDecimal amount;
    private String description;
    private Date date;

    // Constructor
    public Transaction(int userId, String type, String category, BigDecimal amount, String description, Date date) {
        this.userId = userId;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }
}
