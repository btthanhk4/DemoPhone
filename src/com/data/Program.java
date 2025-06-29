package com.data;

import com.data.connection.ConnectionDB;
import com.data.model.Product;

import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        // menu
        showMenu();

        System.out.println("====");
        System.out.println("Nhập chức năng tương ứng:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        while (n < 1 || n > 5) {
            System.out.println("Số chức năng chưa đúng!. Vui lòng nhập lại:");
            n = sc.nextInt();
        }

        switch (n) {
            case 1:
                System.out.println("=== Chức năng Quản lý điện thoại");
                showMenuProduct();

                int numChucNang = 0;
                sc = new Scanner(System.in);
                numChucNang = sc.nextInt();

                while (numChucNang < 1 || numChucNang > 5) {
                    System.out.println("Số chức năng chưa đúng!. Vui lòng nhập lại:");
                    numChucNang = sc.nextInt();
                }
                if (numChucNang == 1) {
                    List<Product> products = productDAO.getListProduct();
                    productDAO.show(products);
                } else if (numChucNang == 2) {
                    sc = new Scanner(System.in);
                    System.out.println("Nhập thông tin điện thoại mới:");

                    System.out.print("Id: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Tên sản phẩm: ");
                    String name = sc.nextLine();

                    System.out.print("Giá: ");
                    int price = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Hãng: ");
                    String brand = sc.nextLine();

                    System.out.print("Tồn kho: ");
                    int stock = sc.nextInt();

                    Product newProduct = new Product(id, name, price, brand, stock);
                    int inserted = productDAO.insert(newProduct);

                    if (inserted > 0) {
                        System.out.println("Thêm sản phẩm thành công!");
                    } else {
                        System.out.println("Thêm sản phẩm thất bại!");
                    }
                } else if (numChucNang == 3) {
                    List<Product> products = productDAO.getListProduct();
                    productDAO.show(products);

                    System.out.print("Nhập id điện thoại cần cập nhật: ");
                    int idUpdate = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();

                    System.out.print("Giá mới: ");
                    int price = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Hãng mới: ");
                    String brand = sc.nextLine();

                    System.out.print("Tồn kho mới: ");
                    int stock = sc.nextInt();

                    Product updatedProduct = new Product(idUpdate, name, price, brand, stock);
                    int updated = productDAO.update(updatedProduct);

                    if (updated > 0) {
                        System.out.println("Cập nhật thành công!");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm để cập nhật.");
                    }
                }
                else if (numChucNang == 4) {
                    // hiển thị danh sách để chọn id
                    List<Product> products = productDAO.getListProduct();
                    productDAO.show(products);

                    System.out.println("Nhập id điện thoại cần xoá:");
                    int id = 0;
                    sc = new Scanner(System.in);
                    id = sc.nextInt();

                    int numAffect = productDAO.delete(id);
                    if (numAffect > 0) {
                        System.out.println("Xoá điện thoại thành công, id = " + id);
                    } else {
                        System.out.println("Xoá không thành công, id không tồn tại");
                    }
                }
                break;
            case 2:
                System.out.println("Chức năng Quản lý khách hàng");
                break;
            case 3:
                System.out.println("Chức năng Quản lý hoá đơn");
                break;
            case 4:
                System.out.println("Chức năng Quản lý doanh thu");
                break;
            default:
                System.out.println("Chức năng Đăng xuất");
        }
    }

    private static void showMenu() {
        // thực hành tạo menu
        System.out.println("==== Chương trình quản lý điện thoại ====");
        System.out.println("1. Quản lý điện thoại");
        System.out.println("2. Quản lý khách hàng");
        System.out.println("3. Quản lý hoá đơn");
        System.out.println("4. Quản lý doanh thu");
        System.out.println("5. Đăng xuất");
    }

    private static void showMenuProduct() {
        System.out.println("==== Chọn chức năng bên dưới: ====");
        System.out.println("1. Xem danh sách điện thoại");
        System.out.println("2. Thêm mới điện thoại");
        System.out.println("3. Cập nhật điện thoại");
        System.out.println("4. Xoá điện thoại theo id");
        System.out.println("5. Trở về");
    }
}