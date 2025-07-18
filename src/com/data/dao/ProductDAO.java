package com.data.dao;

import com.data.model.Product;
import java.util.List;

public interface ProductDAO {
    List<Product> getListProduct();
    int insert(Product product);
    int update(Product product);
    int delete(int id);
    List<Product> searchByBrand(String keyword);
    List<Product> searchByPriceRange(int minPrice, int maxPrice);
    List<Product> searchByStockAvailability(int stockThreshold); // ví dụ: tìm những cái còn tồn
}
