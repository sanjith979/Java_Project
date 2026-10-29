import java.util.ArrayList;
import java.util.Scanner;

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
public class ElectronicShop {

    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Customer> customers = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    // ================= CONSTRUCTOR =================
    ElectronicShop() {

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

    // ================= INPUT HELPERS =================
    String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(sc.nextLine().trim());
    }

    double readDouble(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(sc.nextLine().trim());
    }

    Product findProduct(int id) {
        for (Product p : products) {
            if (p.id == id) return p;
        }
        return null;
    }

    Customer findCustomer(int id) {
        for (Customer c : customers) {
            if (c.id == id) return c;
        }
        return null;
    }

    // ================= DASHBOARD =================
    void showDashboard() {

        while (true) {

            System.out.println("\n====================================");
            System.out.println("  ELECTRONIC SHOP MANAGEMENT SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Product Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Sales");
            System.out.println("4. Inventory");
            System.out.println("5. Warranty");
            System.out.println("6. Exit");

            String choice = readString("Enter choice: ");

            switch (choice) {
                case "1": productMenu(); break;
                case "2": customerMenu(); break;
                case "3": salesMenu(); break;
                case "4": inventory(); break;
                case "5": warranty(); break;
                case "6":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ================= PRODUCT MANAGEMENT =================
    void productMenu() {

        while (true) {

            System.out.println("\n------ PRODUCT MANAGEMENT ------");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Back");

            String choice = readString("Enter choice: ");

            switch (choice) {
                case "1": viewProducts(); break;
                case "2": addProduct(); break;
                case "3": editProduct(); break;
                case "4": deleteProduct(); break;
                case "5": searchProduct(); break;
                case "6": return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    void viewProducts() {

        System.out.println();
        System.out.printf("%-6s %-20s %-12s %-12s %-6s %-10s%n",
                "ID", "Name", "Category", "Price", "Stock", "Warranty");
        System.out.println("-----------------------------------------------------------------------");

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product p : products) {
            System.out.printf("%-6d %-20s %-12s %-12.2f %-6d %-10s%n",
                    p.id, p.name, p.category,
                    p.price, p.stock, p.warranty);
        }
    }

    void addProduct() {

        try {

            int id = readInt("Enter Product ID: ");

            if (findProduct(id) != null) {
                System.out.println("A product with this ID already exists!");
                return;
            }

            String name = readString("Enter Product Name: ");
            String category = readString("Enter Category: ");
            double price = readDouble("Enter Price: ");
            int stock = readInt("Enter Stock: ");
            String warranty = readString("Enter Warranty: ");

            if (name.isEmpty() || category.isEmpty()
                    || warranty.isEmpty()
                    || price < 0 || stock < 0) {

                System.out.println("Invalid Input!");
                return;
            }

            products.add(new Product(
                    id, name, category,
                    price, stock, warranty));

            System.out.println("Product Added Successfully!");

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    void editProduct() {

        try {

            int id = readInt("Enter Product ID to edit: ");

            Product p = findProduct(id);

            if (p == null) {
                System.out.println("Product Not Found!");
                return;
            }

            System.out.println("(Leave a field blank to keep the current value)");

            String newName = readString("Name [" + p.name + "]: ");
            String newCategory = readString("Category [" + p.category + "]: ");
            String newPrice = readString("Price [" + p.price + "]: ");
            String newStock = readString("Stock [" + p.stock + "]: ");
            String newWarranty = readString("Warranty [" + p.warranty + "]: ");

            double price = newPrice.isEmpty() ? p.price : Double.parseDouble(newPrice);
            int stock = newStock.isEmpty() ? p.stock : Integer.parseInt(newStock);

            if (price < 0 || stock < 0) {
                System.out.println("Invalid Input!");
                return;
            }

            if (!newName.isEmpty()) p.name = newName;
            if (!newCategory.isEmpty()) p.category = newCategory;
            if (!newWarranty.isEmpty()) p.warranty = newWarranty;

            p.price = price;
            p.stock = stock;

            System.out.println("Product Updated Successfully!");

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    void deleteProduct() {

        try {

            int id = readInt("Enter Product ID to delete: ");

            boolean removed = products.removeIf(p -> p.id == id);

            if (removed) {
                System.out.println("Product Deleted Successfully!");
            } else {
                System.out.println("Product Not Found!");
            }

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    void searchProduct() {

        String name = readString("Enter product name: ");

        for (Product p : products) {

            if (p.name.equalsIgnoreCase(name)) {

                System.out.println("\nProduct Found!");
                System.out.println("ID       : " + p.id);
                System.out.println("Name     : " + p.name);
                System.out.println("Category : " + p.category);
                System.out.println("Price    : Rs." + p.price);
                System.out.println("Stock    : " + p.stock);
                System.out.println("Warranty : " + p.warranty);
                return;
            }
        }

        System.out.println("Product Not Found!");
    }

    // ================= CUSTOMER MANAGEMENT =================
    void customerMenu() {

        while (true) {

            System.out.println("\n------ CUSTOMER MANAGEMENT ------");
            System.out.println("1. View Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. Edit Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back");

            String choice = readString("Enter choice: ");

            switch (choice) {
                case "1": viewCustomers(); break;
                case "2": addCustomer(); break;
                case "3": editCustomer(); break;
                case "4": deleteCustomer(); break;
                case "5": return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    void viewCustomers() {

        System.out.println();
        System.out.printf("%-6s %-20s %-15s%n", "ID", "Name", "Phone");
        System.out.println("-------------------------------------------");

        if (customers.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }

        for (Customer c : customers) {
            System.out.printf("%-6d %-20s %-15s%n",
                    c.id, c.name, c.phone);
        }
    }

    void addCustomer() {

        try {

            int id = readInt("Enter Customer ID: ");

            if (findCustomer(id) != null) {
                System.out.println("A customer with this ID already exists!");
                return;
            }

            String name = readString("Enter Customer Name: ");
            String phone = readString("Enter Phone Number: ");

            if (name.isEmpty() || phone.isEmpty()) {
                System.out.println("Invalid Input!");
                return;
            }

            customers.add(new Customer(id, name, phone));

            System.out.println("Customer Added Successfully!");

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    void editCustomer() {

        try {

            int id = readInt("Enter Customer ID to edit: ");

            Customer c = findCustomer(id);

            if (c == null) {
                System.out.println("Customer Not Found!");
                return;
            }

            System.out.println("(Leave a field blank to keep the current value)");

            String newName = readString("Name [" + c.name + "]: ");
            String newPhone = readString("Phone [" + c.phone + "]: ");

            if (!newName.isEmpty()) c.name = newName;
            if (!newPhone.isEmpty()) c.phone = newPhone;

            System.out.println("Customer Updated Successfully!");

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    void deleteCustomer() {

        try {

            int id = readInt("Enter Customer ID to delete: ");

            boolean removed = customers.removeIf(c -> c.id == id);

            if (removed) {
                System.out.println("Customer Deleted Successfully!");
            } else {
                System.out.println("Customer Not Found!");
            }

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    // ================= SALES (MULTI-ITEM CART) =================
    void salesMenu() {

        ArrayList<CartItem> cart = new ArrayList<>();

        while (true) {

            System.out.println("\n------------ SALES ------------");
            System.out.println("1. Add to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. Clear Cart");
            System.out.println("5. Generate Bill");
            System.out.println("6. Back");

            String choice = readString("Enter choice: ");

            switch (choice) {
                case "1": addToCart(cart); break;
                case "2": viewCart(cart); break;
                case "3": removeFromCart(cart); break;
                case "4":
                    cart.clear();
                    System.out.println("Cart cleared.");
                    break;
                case "5":
                    if (generateBill(cart)) return;
                    break;
                case "6": return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    void addToCart(ArrayList<CartItem> cart) {

        try {

            int pid = readInt("Enter Product ID: ");
            int qty = readInt("Enter Quantity: ");

            if (qty <= 0) {
                System.out.println("Quantity must be at least 1!");
                return;
            }

            Product selected = findProduct(pid);

            if (selected == null) {
                System.out.println("Product not found!");
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

                System.out.println("Insufficient stock!");
                System.out.println("Available: " + selected.stock);
                System.out.println("Already in cart: " + alreadyInCart);
                return;
            }

            if (existing != null) {
                existing.qty += qty;
            } else {
                cart.add(new CartItem(selected, qty));
            }

            System.out.println("Added to cart.");

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    void viewCart(ArrayList<CartItem> cart) {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        System.out.println();
        System.out.printf("%-4s %-8s %-20s %-10s %-5s %-12s%n",
                "No.", "ID", "Name", "Price", "Qty", "Subtotal");
        System.out.println("------------------------------------------------------------------");

        double total = 0;
        int index = 1;

        for (CartItem item : cart) {

            System.out.printf("%-4d %-8d %-20s %-10.2f %-5d %-12.2f%n",
                    index++,
                    item.product.id,
                    item.product.name,
                    item.product.price,
                    item.qty,
                    item.subtotal());

            total += item.subtotal();
        }

        System.out.println("------------------------------------------------------------------");
        System.out.printf("Total: Rs.%.2f%n", total);
    }

    void removeFromCart(ArrayList<CartItem> cart) {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        viewCart(cart);

        try {

            int no = readInt("Enter item number to remove: ");

            if (no < 1 || no > cart.size()) {
                System.out.println("Invalid item number!");
                return;
            }

            cart.remove(no - 1);

            System.out.println("Item removed.");

        } catch (Exception ex) {

            System.out.println("Invalid Input!");
        }
    }

    // returns true if the bill was generated (so the sales menu can exit)
    boolean generateBill(ArrayList<CartItem> cart) {

        try {

            int cid = readInt("Enter Customer ID: ");

            Customer customer = findCustomer(cid);

            if (customer == null) {
                System.out.println("Customer not found!");
                return false;
            }

            if (cart.isEmpty()) {
                System.out.println("Cart is empty!");
                return false;
            }

            // re-check stock before billing
            for (CartItem item : cart) {

                if (item.qty > item.product.stock) {

                    System.out.println("Insufficient stock for "
                            + item.product.name
                            + "! Available: " + item.product.stock);
                    return false;
                }
            }

            System.out.println("\n============== BILL ==============\n");
            System.out.println("Customer : " + customer.name);
            System.out.println("Phone    : " + customer.phone + "\n");

            System.out.printf("%-18s %4s %10s %12s%n",
                    "Item", "Qty", "Price", "Subtotal");

            System.out.println("--------------------------------------------");

            double grandTotal = 0;

            for (CartItem item : cart) {

                double sub = item.subtotal();
                grandTotal += sub;

                item.product.stock -= item.qty;

                System.out.printf("%-18s %4d %10.2f %12.2f%n",
                        item.product.name,
                        item.qty,
                        item.product.price,
                        sub);
            }

            System.out.println("--------------------------------------------");

            System.out.printf("%-18s %4s %10s %12.2f%n",
                    "TOTAL (Rs.)", "", "", grandTotal);

            System.out.println("\n        Thank You! Visit Again!");

            cart.clear();

            return true;

        } catch (Exception ex) {

            System.out.println("Invalid Customer ID!");
            return false;
        }
    }

    // ================= INVENTORY =================
    void inventory() {

        System.out.println("\n========== INVENTORY ==========\n");

        for (Product p : products) {

            System.out.print(p.id + " | " + p.name + " | Stock: " + p.stock);

            if (p.stock <= 3) {
                System.out.print("  ** LOW STOCK **");
            }

            System.out.println();
        }
    }

    // ================= WARRANTY =================
    void warranty() {

        try {

            int id = readInt("Enter Product ID: ");

            Product p = findProduct(id);

            if (p == null) {
                System.out.println("Product Not Found!");
                return;
            }

            System.out.println("Product : " + p.name);
            System.out.println("Warranty: " + p.warranty);

        } catch (Exception e) {

            System.out.println("Invalid Product ID!");
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        new ElectronicShop();
    }
}