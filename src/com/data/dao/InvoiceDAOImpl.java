// File: com.data.dao.InvoiceDAOImpl.java
package com.data.dao;

import com.data.connection.ConnectionDB;
import com.data.model.Invoice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImpl implements InvoiceDAO {

    @Override
    public List<Invoice> getAll() {
        List<Invoice> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM invoice");
            while (rs.next()) {
                list.add(new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("total")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách hóa đơn!");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int insert(Invoice invoice) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format(
                    "INSERT INTO invoice (id, customer_id, date, total) VALUES (%d, %d, '%s', %.2f)",
                    invoice.getId(), invoice.getCustomerId(), invoice.getDate(), invoice.getTotal()
            );
            return st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Lỗi thêm hóa đơn!");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Invoice> findByCustomerName(String nameKeyword) {
        List<Invoice> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format("""
                    SELECT invoice.* FROM invoice
                    JOIN customer ON invoice.customer_id = customer.id
                    WHERE customer.name LIKE '%%%s%%'
                    """, nameKeyword);

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("total")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi tìm hóa đơn theo tên khách!");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Invoice> findByDate(LocalDate date) {
        List<Invoice> list = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format("SELECT * FROM invoice WHERE date = '%s'", date);
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("total")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi tìm hóa đơn theo ngày!");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public double getTotalByDay(LocalDate date) {
        return getTotal("WHERE date = '" + date + "'");
    }

    @Override
    public double getTotalByMonth(int month, int year) {
        return getTotal("WHERE MONTH(date) = " + month + " AND YEAR(date) = " + year);
    }

    @Override
    public double getTotalByYear(int year) {
        return getTotal("WHERE YEAR(date) = " + year);
    }

    private double getTotal(String condition) {
        double total = 0;
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = "SELECT SUM(total) as total_sum FROM invoice " + condition;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                total = rs.getDouble("total_sum");
            }
        } catch (Exception e) {
            System.out.println("Lỗi tính doanh thu!");
            e.printStackTrace();
        }
        return total;
    }
}
