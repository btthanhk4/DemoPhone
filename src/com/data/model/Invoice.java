package com.data.model;

import java.time.LocalDate;

public class Invoice {
    private int id;
    private int customerId;
    private LocalDate date;
    private double total;

    public Invoice() {}

    public Invoice(int id, int customerId, LocalDate date, double total) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.total = total;
    }

    // Getter – Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("Invoice{id=%d, customerId=%d, date=%s, total=%.2f}",
                id, customerId, date, total);
    }
}
