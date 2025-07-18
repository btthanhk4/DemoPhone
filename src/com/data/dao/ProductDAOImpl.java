package com.data.dao;

import com.data.connection.ConnectionDB;
import com.data.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public List<Product> getListProduct() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM product");
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("brand"),
                        rs.getInt("stock")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách sản phẩm!");
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int insert(Product product) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format(
                    "INSERT INTO product (id, product_name, price, brand, stock) VALUES (%d, '%s', %d, '%s', %d)",
                    product.getId(), product.getProductName(), product.getPrice(),
                    product.getBrand(), product.getStock()
            );
            return st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sản phẩm!");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Product product) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format(
                    "UPDATE product SET product_name = '%s', price = %d, brand = '%s', stock = %d WHERE id = %d",
                    product.getProductName(), product.getPrice(),
                    product.getBrand(), product.getStock(), product.getId()
            );
            return st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sản phẩm!");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int id) {
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = "DELETE FROM product WHERE id = " + id;
            return st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Lỗi khi xoá sản phẩm!");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Product> searchByBrand(String keyword) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = "SELECT * FROM product WHERE brand LIKE '%" + keyword + "%'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("brand"),
                        rs.getInt("stock")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm theo brand!");
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchByPriceRange(int minPrice, int maxPrice) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = String.format("SELECT * FROM product WHERE price BETWEEN %d AND %d", minPrice, maxPrice);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("brand"),
                        rs.getInt("stock")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm theo khoảng giá!");
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchByStockAvailability(int stockThreshold) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             Statement st = conn.createStatement()) {

            String sql = "SELECT * FROM product WHERE stock <= " + stockThreshold;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("brand"),
                        rs.getInt("stock")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm theo tồn kho!");
            e.printStackTrace();
        }
        return products;
    }
}
