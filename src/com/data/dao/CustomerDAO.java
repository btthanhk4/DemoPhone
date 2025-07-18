package com.data.dao;

import com.data.model.Customer;
import java.util.List;

public interface CustomerDAO {
    List<Customer> getAll();
    int insert(Customer customer);
    int update(Customer customer);
    int delete(int id);
    Customer findById(int id);
}
