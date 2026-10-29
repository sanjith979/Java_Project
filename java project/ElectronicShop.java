import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// ================= PRODUCT =================
class Product {
    int id;
    String name;
    String category;
    double price;
    int stock;
    String warranty;

    Product(int id, String name, String category,
            double price, int stock, String warranty) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.warranty = warranty;
    }
}

// ================= CUSTOMER =================
class Customer {
    int id;
    String name;
    String phone;

    Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}

// ================= MAIN CLASS =================
public class ElectronicShop extends JFrame {

    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Customer> customers = new ArrayList<>();

    DefaultTableModel productModel;
    DefaultTableModel customerModel;

    // ================= CONSTRUCTOR =================
    ElectronicShop() {

        setTitle("Electronic Shop Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        products.add(new Product(
                101, "Samsung S25", "Mobile",
                79999, 10, "1 Year"));

        products.add(new Product(
                102, "HP Victus", "Laptop",
                65000, 5, "2 Years"));

        products.add(new Product(
                103, "Sony Bravia", "TV",
                55000, 7, "2 Years"));

        products.add(new Product(
                104, "Sony Headphones", "Audio",
                5000, 15, "1 Year"));

        showDashboard();
    }

    // ================= DASHBOARD =================
    void showDashboard() {

        JPanel panel = new JPanel(new GridLayout(3, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(
                50, 100, 50, 100));

        JButton productBtn = new JButton("PRODUCT MANAGEMENT");
        JButton customerBtn = new JButton("CUSTOMER MANAGEMENT");
        JButton salesBtn = new JButton("SALES");
        JButton stockBtn = new JButton("INVENTORY");
        JButton warrantyBtn = new JButton("WARRANTY");
        JButton exitBtn = new JButton("EXIT");

        panel.add(productBtn);
        panel.add(customerBtn);
        panel.add(salesBtn);
        panel.add(stockBtn);
        panel.add(warrantyBtn);
        panel.add(exitBtn);

        add(panel);

        productBtn.addActionListener(e -> productWindow());
        customerBtn.addActionListener(e -> customerWindow());
        salesBtn.addActionListener(e -> salesWindow());
        stockBtn.addActionListener(e -> inventoryWindow());
        warrantyBtn.addActionListener(e -> warrantyWindow());

        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // ================= PRODUCT MANAGEMENT =================
    void productWindow() {

        JFrame frame = new JFrame("Product Management");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(this);

        productModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Category",
                        "Price", "Stock", "Warranty"}, 0) {

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(productModel);

        refreshProducts();

        JButton add = new JButton("Add Product");
        JButton edit = new JButton("Edit Product");
        JButton delete = new JButton("Delete Product");
        JButton search = new JButton("Search Product");

        JPanel bottom = new JPanel();

        bottom.add(add);
        bottom.add(edit);
        bottom.add(delete);
        bottom.add(search);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        // ADD PRODUCT
        add.addActionListener(e -> {

            try {

                int id = Integer.parseInt(
                        JOptionPane.showInputDialog(
                                frame, "Enter Product ID"));

                String name = JOptionPane.showInputDialog(
                        frame, "Enter Product Name");

                String category = JOptionPane.showInputDialog(
                        frame, "Enter Category");

                double price = Double.parseDouble(
                        JOptionPane.showInputDialog(
                                frame, "Enter Price"));

                int stock = Integer.parseInt(
                        JOptionPane.showInputDialog(
                                frame, "Enter Stock"));

                String warranty = JOptionPane.showInputDialog(
                        frame, "Enter Warranty");

                products.add(new Product(
                        id, name, category,
                        price, stock, warranty));

                refreshProducts();

                JOptionPane.showMessageDialog(
                        frame, "Product Added Successfully!");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame, "Invalid Input!");
            }
        });

        // EDIT PRODUCT
        edit.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                        frame, "Select a product first!");
                return;
            }

            int id = (int) productModel.getValueAt(row, 0);

            Product p = null;

            for (Product item : products) {
                if (item.id == id) {
                    p = item;
                    break;
                }
            }

            if (p == null) return;

            JTextField idField = new JTextField(String.valueOf(p.id));
            idField.setEditable(false);

            JTextField nameField = new JTextField(p.name);
            JTextField categoryField = new JTextField(p.category);
            JTextField priceField = new JTextField(String.valueOf(p.price));
            JTextField stockField = new JTextField(String.valueOf(p.stock));
            JTextField warrantyField = new JTextField(p.warranty);

            JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));

            form.add(new JLabel("ID (locked):"));
            form.add(idField);
            form.add(new JLabel("Name:"));
            form.add(nameField);
            form.add(new JLabel("Category:"));
            form.add(categoryField);
            form.add(new JLabel("Price:"));
            form.add(priceField);
            form.add(new JLabel("Stock:"));
            form.add(stockField);
            form.add(new JLabel("Warranty:"));
            form.add(warrantyField);

            int option = JOptionPane.showConfirmDialog(
                    frame, form, "Edit Product",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (option != JOptionPane.OK_OPTION) return;

            try {

                String newName = nameField.getText().trim();
                String newCategory = categoryField.getText().trim();
                double newPrice = Double.parseDouble(
                        priceField.getText().trim());
                int newStock = Integer.parseInt(
                        stockField.getText().trim());
                String newWarranty = warrantyField.getText().trim();

                if (newName.isEmpty() || newCategory.isEmpty()
                        || newWarranty.isEmpty()
                        || newPrice < 0 || newStock < 0) {

                    JOptionPane.showMessageDialog(
                            frame, "Invalid Input!");
                    return;
                }

                p.name = newName;
                p.category = newCategory;
                p.price = newPrice;
                p.stock = newStock;
                p.warranty = newWarranty;

                refreshProducts();

                JOptionPane.showMessageDialog(
                        frame, "Product Updated Successfully!");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame, "Invalid Input!");
            }
        });

        // DELETE PRODUCT
        delete.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                        frame, "Select a product first!");
                return;
            }

            int id = (int) productModel.getValueAt(row, 0);

            products.removeIf(p -> p.id == id);

            refreshProducts();
        });

        // SEARCH PRODUCT
        search.addActionListener(e -> {

            String name = JOptionPane.showInputDialog(
                    frame, "Enter product name");

            for (Product p : products) {

                if (p.name.equalsIgnoreCase(name)) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Product Found!\n\n" +
                            "ID: " + p.id +
                            "\nName: " + p.name +
                            "\nCategory: " + p.category +
                            "\nPrice: ₹" + p.price +
                            "\nStock: " + p.stock +
                            "\nWarranty: " + p.warranty);

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    frame, "Product Not Found!");
        });

        frame.setVisible(true);
    }

    // ================= REFRESH PRODUCTS =================
    void refreshProducts() {

        if (productModel == null)
            return;

        productModel.setRowCount(0);

        for (Product p : products) {

            productModel.addRow(new Object[]{
                    p.id,
                    p.name,
                    p.category,
                    p.price,
                    p.stock,
                    p.warranty
            });
        }
    }

    // ================= CUSTOMER MANAGEMENT =================
    void customerWindow() {

        JFrame frame = new JFrame("Customer Management");
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(this);

        customerModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Phone"}, 0) {

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(customerModel);

        refreshCustomers();

        JButton add = new JButton("Add Customer");
        JButton edit = new JButton("Edit Customer");
        JButton delete = new JButton("Delete Customer");

        JPanel bottom = new JPanel();

        bottom.add(add);
        bottom.add(edit);
        bottom.add(delete);

        frame.add(new JScrollPane(table),
                BorderLayout.CENTER);

        frame.add(bottom, BorderLayout.SOUTH);

        // ADD CUSTOMER
        add.addActionListener(e -> {

            try {

                int id = Integer.parseInt(
                        JOptionPane.showInputDialog(
                                frame, "Enter Customer ID"));

                String name = JOptionPane.showInputDialog(
                        frame, "Enter Customer Name");

                String phone = JOptionPane.showInputDialog(
                        frame, "Enter Phone Number");

                customers.add(
                        new Customer(id, name, phone));

                refreshCustomers();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame, "Invalid Input!");
            }
        });

        // EDIT CUSTOMER
        edit.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                        frame, "Select a customer first!");
                return;
            }

            int id = (int) customerModel.getValueAt(row, 0);

            Customer c = null;

            for (Customer item : customers) {
                if (item.id == id) {
                    c = item;
                    break;
                }
            }

            if (c == null) return;

            JTextField idField = new JTextField(String.valueOf(c.id));
            idField.setEditable(false);

            JTextField nameField = new JTextField(c.name);
            JTextField phoneField = new JTextField(c.phone);

            JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));

            form.add(new JLabel("ID (locked):"));
            form.add(idField);
            form.add(new JLabel("Name:"));
            form.add(nameField);
            form.add(new JLabel("Phone:"));
            form.add(phoneField);

            int option = JOptionPane.showConfirmDialog(
                    frame, form, "Edit Customer",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (option != JOptionPane.OK_OPTION) return;

            String newName = nameField.getText().trim();
            String newPhone = phoneField.getText().trim();

            if (newName.isEmpty() || newPhone.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame, "Invalid Input!");
                return;
            }

            c.name = newName;
            c.phone = newPhone;

            refreshCustomers();

            JOptionPane.showMessageDialog(
                    frame, "Customer Updated Successfully!");
        });

        // DELETE CUSTOMER
        delete.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                        frame, "Select a customer!");
                return;
            }

            int id = (int) customerModel.getValueAt(row, 0);

            customers.removeIf(c -> c.id == id);

            refreshCustomers();
        });

        frame.setVisible(true);
    }

    // ================= REFRESH CUSTOMERS =================
    void refreshCustomers() {

        if (customerModel == null)
            return;

        customerModel.setRowCount(0);

        for (Customer c : customers) {

            customerModel.addRow(new Object[]{
                    c.id,
                    c.name,
                    c.phone
            });
        }
    }

    // ================= SALES =================
    void salesWindow() {

        JFrame frame = new JFrame("Sales");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30));

        JTextField customerId =
                new JTextField();

        JTextField productId =
                new JTextField();

        JTextField quantity =
                new JTextField();

        JButton sell =
                new JButton("Generate Bill");

        panel.add(new JLabel("Customer ID:"));
        panel.add(customerId);

        panel.add(new JLabel("Product ID:"));
        panel.add(productId);

        panel.add(new JLabel("Quantity:"));
        panel.add(quantity);

        panel.add(new JLabel(""));
        panel.add(sell);

        frame.add(panel);

        sell.addActionListener(e -> {

            try {

                int cid = Integer.parseInt(
                        customerId.getText());

                int pid = Integer.parseInt(
                        productId.getText());

                int qty = Integer.parseInt(
                        quantity.getText());

                Product selected = null;
                Customer customer = null;

                for (Product p : products) {

                    if (p.id == pid) {
                        selected = p;
                        break;
                    }
                }

                for (Customer c : customers) {

                    if (c.id == cid) {
                        customer = c;
                        break;
                    }
                }

                if (selected == null) {

                    JOptionPane.showMessageDialog(
                            frame, "Product not found!");

                    return;
                }

                if (customer == null) {

                    JOptionPane.showMessageDialog(
                            frame, "Customer not found!");

                    return;
                }

                if (qty > selected.stock) {

                    JOptionPane.showMessageDialog(
                            frame, "Insufficient stock!");

                    return;
                }

                double total =
                        selected.price * qty;

                selected.stock -= qty;

                refreshProducts();

                JOptionPane.showMessageDialog(
                        frame,

                        "========== BILL ==========\n\n" +

                        "Customer: " +
                        customer.name +

                        "\nProduct: " +
                        selected.name +

                        "\nQuantity: " +
                        qty +

                        "\nPrice: ₹" +
                        selected.price +

                        "\n--------------------------" +

                        "\nTOTAL: ₹" +
                        total +

                        "\n\nThank You!");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame, "Invalid Input!");
            }
        });

        frame.setVisible(true);
    }

    // ================= INVENTORY =================
    void inventoryWindow() {

        StringBuilder result =
                new StringBuilder();

        result.append(
                "========== INVENTORY ==========\n\n");

        for (Product p : products) {

            result.append(
                    p.id + " | " +
                    p.name + " | Stock: " +
                    p.stock);

            if (p.stock <= 3) {

                result.append("  ⚠ LOW STOCK");
            }

            result.append("\n");
        }

        JTextArea area =
                new JTextArea(result.toString());

        area.setEditable(false);

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                "Inventory",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ================= WARRANTY =================
    void warrantyWindow() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Product ID");

        try {

            int id = Integer.parseInt(input);

            for (Product p : products) {

                if (p.id == id) {

                    JOptionPane.showMessageDialog(
                            this,

                            "Product: " + p.name +

                            "\nWarranty: " +
                            p.warranty);

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this, "Product Not Found!");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this, "Invalid Product ID!");
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        new ElectronicShop();
    }
}