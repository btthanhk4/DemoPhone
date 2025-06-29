package com.data;

import com.data.connection.ConnectionDB;
import com.data.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl {

    public List<Product> getListProduct() {
        Connection conn = null;
        List<Product> products = new ArrayList<>();

        try {
            conn = ConnectionDB.openConn();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM product");
            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("product_name");
                int price = rs.getInt("price");
                String brand = rs.getString("brand");
                int stock = rs.getInt("stock");

                Product product = new Product(id, productName, price, brand, stock);
                products.add(product);
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy dữ liệu!");
        }

        return products;
    }

    public void show(List<Product> products) {
        System.out.println("==== Danh sách sản phẩm ====");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-4s | %-25s | %-8s | %-10s | %-5s |\n", "Id", "Product Name", "Price", "Brand", "Stock");
        System.out.println("--------------------------------------------------------------");

        for (Product product : products) {
            System.out.printf("| %-4d | %-25s | %-8d | %-10s | %-5d |\n",
                    product.getId(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getBrand(),
                    product.getStock());
        }

        System.out.println("--------------------------------------------------------------");
    }

    public int insert(Product product) {
        int result = 0;
        try (Connection conn = ConnectionDB.openConn();
             Statement st = conn.createStatement()) {

            String sql = String.format(
                    "INSERT INTO product (id, product_name, price, brand, stock) VALUES (%d, '%s', %d, '%s', %d)",
                    product.getId(), product.getProductName(), product.getPrice(), product.getBrand(), product.getStock()
            );
            result = st.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sản phẩm!");
            e.printStackTrace();
        }
        return result;
    }

    public int update(Product product) {
        int result = 0;
        try (Connection conn = ConnectionDB.openConn();
             Statement st = conn.createStatement()) {

            String sql = String.format(
                    "UPDATE product SET product_name = '%s', price = %d, brand = '%s', stock = %d WHERE id = %d",
                    product.getProductName(), product.getPrice(), product.getBrand(), product.getStock(), product.getId()
            );
            result = st.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sản phẩm!");
            e.printStackTrace();
        }
        return result;
    }


    public int delete(int id) {
        Connection conn = null;
        int countAffect = 0;
        try {
            conn = ConnectionDB.openConn();
            Statement st = conn.createStatement();

            countAffect = st.executeUpdate("DELETE FROM product WHERE id = " + id);
        } catch (Exception e) {
            System.out.println("Lỗi lấy dữ liệu!");
        }

        return countAffect;
    }
}