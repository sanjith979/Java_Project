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

// ================= CART ITEM =================
class CartItem {
    Product product;
    int qty;

    CartItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    double subtotal() {
        return product.price * qty;
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

    // ================= SALES (MULTI-ITEM CART) =================
    void salesWindow() {

        JFrame frame = new JFrame("Sales");
        frame.setSize(750, 600);
        frame.setLocationRelativeTo(this);
        frame.setLayout(new BorderLayout(10, 10));

        ArrayList<CartItem> cart = new ArrayList<>();

        // ---------- TOP: input fields ----------
        JPanel top = new JPanel(new GridLayout(4, 2, 10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(
                20, 30, 10, 30));

        JTextField customerId = new JTextField();
        JTextField productId = new JTextField();
        JTextField quantity = new JTextField();
        JButton addToCart = new JButton("Add to Cart");

        top.add(new JLabel("Customer ID:"));
        top.add(customerId);

        top.add(new JLabel("Product ID:"));
        top.add(productId);

        top.add(new JLabel("Quantity:"));
        top.add(quantity);

        top.add(new JLabel(""));
        top.add(addToCart);

        // ---------- CENTER: cart table ----------
        DefaultTableModel cartModel = new DefaultTableModel(
                new String[]{"Product ID", "Name", "Price",
                        "Qty", "Subtotal"}, 0) {

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable cartTable = new JTable(cartModel);

        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setBorder(BorderFactory.createTitledBorder("Cart"));

        // ---------- BOTTOM: total + buttons ----------
        JLabel totalLabel = new JLabel("Total: ₹0.0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton remove = new JButton("Remove Selected");
        JButton clear = new JButton("Clear Cart");
        JButton bill = new JButton("Generate Bill");

        JPanel bottom = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 15, 10));

        bottom.add(remove);
        bottom.add(clear);
        bottom.add(totalLabel);
        bottom.add(bill);

        frame.add(top, BorderLayout.NORTH);
        frame.add(cartScroll, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        // ---------- ADD TO CART ----------
        addToCart.addActionListener(e -> {

            try {

                int pid = Integer.parseInt(
                        productId.getText().trim());

                int qty = Integer.parseInt(
                        quantity.getText().trim());

                if (qty <= 0) {
                    JOptionPane.showMessageDialog(
                            frame, "Quantity must be at least 1!");
                    return;
                }

                Product selected = null;

                for (Product p : products) {
                    if (p.id == pid) {
                        selected = p;
                        break;
                    }
                }

                if (selected == null) {
                    JOptionPane.showMessageDialog(
                            frame, "Product not found!");
                    return;
                }

                CartItem existing = null;

                for (CartItem item : cart) {
                    if (item.product.id == pid) {
                        existing = item;
                        break;
                    }
                }

                int alreadyInCart = (existing == null) ? 0 : existing.qty;

                if (alreadyInCart + qty > selected.stock) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Insufficient stock!\n" +
                            "Available: " + selected.stock +
                            "\nAlready in cart: " + alreadyInCart);
                    return;
                }

                if (existing != null) {
                    existing.qty += qty;
                } else {
                    cart.add(new CartItem(selected, qty));
                }

                refreshCart(cartModel, cart, totalLabel);

                productId.setText("");
                quantity.setText("");
                productId.requestFocus();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame, "Invalid Input!");
            }
        });

        // ---------- REMOVE SELECTED ----------
        remove.addActionListener(e -> {

            int row = cartTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(
                        frame, "Select an item in the cart!");
                return;
            }

            cart.remove(row);

            refreshCart(cartModel, cart, totalLabel);
        });

        // ---------- CLEAR CART ----------
        clear.addActionListener(e -> {

            cart.clear();

            refreshCart(cartModel, cart, totalLabel);
        });

        // ---------- GENERATE BILL ----------
        bill.addActionListener(e -> {

            try {

                int cid = Integer.parseInt(
                        customerId.getText().trim());

                Customer customer = null;

                for (Customer c : customers) {
                    if (c.id == cid) {
                        customer = c;
                        break;
                    }
                }

                if (customer == null) {
                    JOptionPane.showMessageDialog(
                            frame, "Customer not found!");
                    return;
                }

                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            frame, "Cart is empty!");
                    return;
                }

                // re-check stock before billing
                for (CartItem item : cart) {

                    if (item.qty > item.product.stock) {

                        JOptionPane.showMessageDialog(
                                frame,
                                "Insufficient stock for " +
                                item.product.name +
                                "!\nAvailable: " +
                                item.product.stock);
                        return;
                    }
                }

                StringBuilder receipt = new StringBuilder();

                receipt.append("============== BILL ==============\n\n");
                receipt.append("Customer : ").append(customer.name).append("\n");
                receipt.append("Phone    : ").append(customer.phone).append("\n\n");

                receipt.append(String.format(
                        "%-18s %4s %10s %12s\n",
                        "Item", "Qty", "Price", "Subtotal"));

                receipt.append("--------------------------------------------\n");

                double grandTotal = 0;

                for (CartItem item : cart) {

                    double sub = item.subtotal();
                    grandTotal += sub;

                    item.product.stock -= item.qty;

                    receipt.append(String.format(
                            "%-18s %4d %10.2f %12.2f\n",
                            item.product.name,
                            item.qty,
                            item.product.price,
                            sub));
                }

                receipt.append("--------------------------------------------\n");

                receipt.append(String.format(
                        "%-18s %4s %10s %12.2f\n",
                        "TOTAL (Rs.)", "", "", grandTotal));

                receipt.append("\n        Thank You! Visit Again!");

                JTextArea area = new JTextArea(receipt.toString());
                area.setFont(new Font("Monospaced", Font.PLAIN, 13));
                area.setEditable(false);

                JScrollPane scroll = new JScrollPane(area);
                scroll.setPreferredSize(new Dimension(480, 320));

                JOptionPane.showMessageDialog(
                        frame, scroll, "Bill",
                        JOptionPane.INFORMATION_MESSAGE);

                cart.clear();

                refreshCart(cartModel, cart, totalLabel);
                refreshProducts();

                customerId.setText("");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame, "Invalid Customer ID!");
            }
        });

        frame.setVisible(true);
    }

    // ================= REFRESH CART =================
    void refreshCart(DefaultTableModel model,
                     ArrayList<CartItem> cart,
                     JLabel totalLabel) {

        model.setRowCount(0);

        double total = 0;

        for (CartItem item : cart) {

            model.addRow(new Object[]{
                    item.product.id,
                    item.product.name,
                    item.product.price,
                    item.qty,
                    item.subtotal()
            });

            total += item.subtotal();
        }

        totalLabel.setText("Total: ₹" + total);
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