package com.data.dao;

import com.data.model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceDAO {
    List<Invoice> getAll();
    int insert(Invoice invoice);
    List<Invoice> findByCustomerName(String nameKeyword);
    List<Invoice> findByDate(LocalDate date);
    double getTotalByDay(LocalDate date);
    double getTotalByMonth(int month, int year);
    double getTotalByYear(int year);
}
