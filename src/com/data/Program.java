//user: admin
//pass: 1234

package com.data;

import com.data.dao.*;
import com.data.model.*;
import com.data.dao.AdminDAO;
import com.data.dao.AdminDAOImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAOImpl();
        CustomerDAO customerDAO = new CustomerDAOImpl();
        InvoiceDAO invoiceDAO = new InvoiceDAOImpl();
        Scanner sc = new Scanner(System.in);
        AdminDAO adminDAO = new AdminDAOImpl();
        boolean authenticated = false;

        while (!authenticated) {
            System.out.print("Nhập username: ");
            String username = sc.nextLine();
            System.out.print("Nhập password: ");
            String password = sc.nextLine();

            if (adminDAO.login(username, password)) {
                System.out.println("Đăng nhập thành công!\n");
                authenticated = true;
            } else {
                System.out.println("Sai username hoặc password. Vui lòng thử lại!\n");
            }
        }

        while (true) {
            showMainMenu();
            System.out.print("Chọn chức năng (1-6): ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> menuProduct(productDAO, sc);
                case 2 -> menuCustomer(customerDAO, sc);
                case 3 -> menuInvoice(invoiceDAO, sc);
                case 4 -> menuRevenue(invoiceDAO, sc);
                case 5 -> System.out.println("Đăng nhập Admin (chưa triển khai)");
                case 6 -> {
                    System.out.println("Đã đăng xuất. Tạm biệt!");
                    return;
                }
                default -> System.out.println("Chức năng không hợp lệ. Nhập lại!");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n========= MENU CHÍNH =========");
        System.out.println("1. Quản lý điện thoại");
        System.out.println("2. Quản lý khách hàng");
        System.out.println("3. Quản lý hóa đơn");
        System.out.println("4. Thống kê doanh thu");
        System.out.println("5. Đăng nhập admin (chưa làm)");
        System.out.println("6. Thoát");
    }

    // ===== MENU ĐIỆN THOẠI =====
    private static void menuProduct(ProductDAO dao, Scanner sc) {
        while (true) {
            System.out.println("\n--- Quản lý điện thoại ---");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xoá theo ID");
            System.out.println("5. Tìm theo Brand");
            System.out.println("6. Tìm theo khoảng giá");
            System.out.println("7. Tìm theo tồn kho");
            System.out.println("8. Quay lại menu chính");
            System.out.print("Chọn: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> showProductList(dao.getListProduct());
                case 2 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Tên SP: ");
                    String name = sc.nextLine();
                    System.out.print("Giá: ");
                    int price = sc.nextInt(); sc.nextLine();
                    System.out.print("Hãng: ");
                    String brand = sc.nextLine();
                    System.out.print("Tồn kho: ");
                    int stock = sc.nextInt();
                    Product p = new Product(id, name, price, brand, stock);
                    System.out.println(dao.insert(p) > 0 ? "Thêm thành công!" : "Thêm thất bại!");
                }
                case 3 -> {
                    System.out.print("Nhập ID cần cập nhật: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();
                    System.out.print("Giá mới: ");
                    int price = sc.nextInt(); sc.nextLine();
                    System.out.print("Hãng mới: ");
                    String brand = sc.nextLine();
                    System.out.print("Tồn kho mới: ");
                    int stock = sc.nextInt();
                    Product p = new Product(id, name, price, brand, stock);
                    System.out.println(dao.update(p) > 0 ? "Cập nhật thành công!" : "Không tìm thấy ID.");
                }
                case 4 -> {
                    System.out.print("Nhập ID cần xoá: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.println(dao.delete(id) > 0 ? "Đã xoá!" : "ID không tồn tại.");
                }
                case 5 -> {
                    System.out.print("Từ khoá brand: ");
                    String key = sc.nextLine();
                    showProductList(dao.searchByBrand(key));
                }
                case 6 -> {
                    System.out.print("Giá MIN: ");
                    int min = sc.nextInt();
                    System.out.print("Giá MAX: ");
                    int max = sc.nextInt();
                    showProductList(dao.searchByPriceRange(min, max));
                }
                case 7 -> {
                    System.out.print("Tồn kho <= ");
                    int stock = sc.nextInt();
                    showProductList(dao.searchByStockAvailability(stock));
                }
                case 8 -> { return; }
                default -> System.out.println("Không hợp lệ!");
            }
        }
    }

    private static void showProductList(List<Product> products) {
        System.out.println("\n===== DANH SÁCH SẢN PHẨM =====");
        System.out.printf("| %-4s | %-25s | %-8s | %-10s | %-5s |\n", "ID", "Tên SP", "Giá", "Hãng", "Kho");
        System.out.println("--------------------------------------------------------------");
        for (Product p : products) {
            System.out.printf("| %-4d | %-25s | %-8d | %-10s | %-5d |\n",
                    p.getId(), p.getProductName(), p.getPrice(), p.getBrand(), p.getStock());
        }
    }

    // ===== MENU KHÁCH HÀNG =====
    private static void menuCustomer(CustomerDAO dao, Scanner sc) {
        while (true) {
            System.out.println("\n--- Quản lý khách hàng ---");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xoá theo ID");
            System.out.println("5. Quay lại");
            System.out.print("Chọn: ");
            int opt = sc.nextInt(); sc.nextLine();

            switch (opt) {
                case 1 -> {
                    List<Customer> list = dao.getAll();
                    System.out.println("\n===== DANH SÁCH KHÁCH HÀNG =====");
                    System.out.printf("| %-4s | %-20s | %-12s | %-25s |\n", "ID", "Tên KH", "SĐT", "Email");
                    System.out.println("-----------------------------------------------------------------------");
                    for (Customer c : list) {
                        System.out.printf("| %-4d | %-20s | %-12s | %-25s |\n",
                                c.getId(), c.getName(), c.getPhone(), c.getEmail());
                    }
                }
                case 2 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Tên KH: ");
                    String name = sc.nextLine();
                    System.out.print("SĐT: ");
                    String phone = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    Customer c = new Customer(id, name, phone, email);
                    System.out.println(dao.insert(c) > 0 ? "Thêm thành công!" : "Thêm thất bại.");
                }
                case 3 -> {
                    System.out.print("Nhập ID cần cập nhật: ");
                    int id = sc.nextInt(); sc.nextLine();
                    Customer old = dao.findById(id);
                    if (old == null) {
                        System.out.println("Không tìm thấy ID khách hàng!");
                        break;
                    }
                    System.out.print("Tên mới: ");
                    String name = sc.nextLine();
                    System.out.print("SĐT mới: ");
                    String phone = sc.nextLine();
                    System.out.print("Email mới: ");
                    String email = sc.nextLine();
                    Customer c = new Customer(id, name, phone, email);
                    System.out.println(dao.update(c) > 0 ? "Cập nhật thành công!" : "Lỗi cập nhật.");
                }
                case 4 -> {
                    System.out.print("Nhập ID cần xoá: ");
                    int id = sc.nextInt(); sc.nextLine();
                    Customer c = dao.findById(id);
                    if (c == null) {
                        System.out.println("Không tìm thấy ID!");
                        break;
                    }
                    System.out.print("Xác nhận xoá (y/n)? ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        System.out.println(dao.delete(id) > 0 ? "Đã xoá!" : "Xoá thất bại.");
                    } else {
                        System.out.println("Huỷ xoá.");
                    }
                }
                case 5 -> { return; }
                default -> System.out.println("Không hợp lệ!");
            }
        }
    }

    // ===== MENU HOÁ ĐƠN =====
    private static void menuInvoice(InvoiceDAO dao, Scanner sc) {
        while (true) {
            System.out.println("\n--- Quản lý hoá đơn ---");
            System.out.println("1. Thêm hoá đơn");
            System.out.println("2. Xem tất cả hoá đơn");
            System.out.println("3. Tìm kiếm theo tên khách hàng");
            System.out.println("4. Tìm kiếm theo ngày");
            System.out.println("5. Quay lại");
            System.out.print("Chọn: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> {
                    System.out.print("ID hóa đơn: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("ID khách hàng: ");
                    int customerId = sc.nextInt(); sc.nextLine();
                    System.out.print("Ngày (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(sc.nextLine());
                    System.out.print("Tổng tiền: ");
                    double total = sc.nextDouble();
                    Invoice inv = new Invoice(id, customerId, date, total);
                    System.out.println(dao.insert(inv) > 0 ? "Thêm thành công!" : "Lỗi thêm!");
                }
                case 2 -> showInvoiceList(dao.getAll());
                case 3 -> {
                    System.out.print("Từ khoá tên KH: ");
                    String keyword = sc.nextLine();
                    showInvoiceList(dao.findByCustomerName(keyword));
                }
                case 4 -> {
                    System.out.print("Ngày cần tìm (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(sc.nextLine());
                    showInvoiceList(dao.findByDate(date));
                }
                case 5 -> { return; }
                default -> System.out.println("Chức năng không hợp lệ!");
            }
        }
    }

    private static void showInvoiceList(List<Invoice> invoices) {
        System.out.println("\n===== DANH SÁCH HÓA ĐƠN =====");
        System.out.printf("| %-4s | %-10s | %-12s | %-10s |\n", "ID", "Customer", "Ngày", "Tổng tiền");
        System.out.println("-----------------------------------------------");
        for (Invoice inv : invoices) {
            System.out.printf("| %-4d | %-10d | %-12s | %-10.2f |\n",
                    inv.getId(), inv.getCustomerId(), inv.getDate(), inv.getTotal());
        }
    }

    // ===== THỐNG KÊ DOANH THU =====
    private static void menuRevenue(InvoiceDAO dao, Scanner sc) {
        while (true) {
            System.out.println("\n--- Thống kê doanh thu ---");
            System.out.println("1. Doanh thu theo ngày");
            System.out.println("2. Doanh thu theo tháng");
            System.out.println("3. Doanh thu theo năm");
            System.out.println("4. Quay lại");
            System.out.print("Chọn: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1 -> {
                    System.out.print("Nhập ngày (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(sc.nextLine());
                    double total = dao.getTotalByDay(date);
                    System.out.printf("Doanh thu ngày %s là: %.2f\n", date, total);
                }
                case 2 -> {
                    System.out.print("Tháng: ");
                    int month = sc.nextInt();
                    System.out.print("Năm: ");
                    int year = sc.nextInt();
                    double total = dao.getTotalByMonth(month, year);
                    System.out.printf("Doanh thu tháng %d/%d là: %.2f\n", month, year, total);
                }
                case 3 -> {
                    System.out.print("Năm: ");
                    int year = sc.nextInt();
                    double total = dao.getTotalByYear(year);
                    System.out.printf("Doanh thu năm %d là: %.2f\n", year, total);
                }
                case 4 -> { return; }
                default -> System.out.println("Không hợp lệ!");
            }
        }
    }
}
