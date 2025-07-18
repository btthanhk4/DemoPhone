package com.data.dao;

import com.data.connection.ConnectionDB;
import com.data.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách khách hàng!");
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public int insert(Customer customer) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format(
                    "INSERT INTO customer (id, name, phone, email) VALUES (%d, '%s', '%s', '%s')",
                    customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail()
            );
            return st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm khách hàng!");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Customer customer) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format(
                    "UPDATE customer SET name='%s', phone='%s', email='%s' WHERE id=%d",
                    customer.getName(), customer.getPhone(), customer.getEmail(), customer.getId()
            );
            return st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật khách hàng!");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int id) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = "DELETE FROM customer WHERE id = " + id;
            return st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Lỗi khi xoá khách hàng!");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Customer findById(int id) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = "SELECT * FROM customer WHERE id = " + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm khách hàng theo ID!");
            e.printStackTrace();
        }
        return null;
    }
}
