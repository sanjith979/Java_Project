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

    Product(int id, String name, String category, double price, int stock, String warranty) {
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
    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<Customer> customers = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    ElectronicShop() {
        products.add(new Product(101, "Samsung S25", "Mobile", 79999, 10, "1 Year"));
        products.add(new Product(102, "HP Victus", "Laptop", 65000, 5, "2 Years"));
        products.add(new Product(103, "Sony Bravia", "TV", 55000, 7, "2 Years"));
        products.add(new Product(104, "Sony Headphones", "Audio", 5000, 15, "1 Year"));
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(sc.nextLine().trim());
                if (value < 0) {
                    System.out.println("Value cannot be negative.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid number.");
            }
        }
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty.");
        }
    }

    private Product findProduct(int id) {
        for (Product p : products) {
            if (p.id == id) return p;
        }
        return null;
    }

    private Customer findCustomer(int id) {
        for (Customer c : customers) {
            if (c.id == id) return c;
        }
        return null;
    }

    // ================= MAIN MENU =================
    void run() {
        while (true) {
            System.out.println("\\n========== ELECTRONIC SHOP MANAGEMENT SYSTEM ==========");
            System.out.println("1. Product Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Sales");
            System.out.println("4. Inventory");
            System.out.println("5. Warranty");
            System.out.println("6. Exit");
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: productMenu(); break;
                case 2: customerMenu(); break;
                case 3: salesMenu(); break;
                case 4: showInventory(); break;
                case 5: showWarranty(); break;
                case 6:
                    System.out.println("Thank you for using the Electronic Shop Management System!");
                    sc.close();
                    return;
                default: System.out.println("Invalid choice. Choose 1-6.");
            }
        }
    }

    // ================= PRODUCT MANAGEMENT =================
    private void productMenu() {
        while (true) {
            System.out.println("\\n---------- PRODUCT MANAGEMENT ----------");
            System.out.println("1. Display Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Back to Main Menu");
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: displayProducts(); break;
                case 2: addProduct(); break;
                case 3: editProduct(); break;
                case 4: deleteProduct(); break;
                case 5: searchProduct(); break;
                case 6: return;
                default: System.out.println("Invalid choice. Choose 1-6.");
            }
        }
    }

    private void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.printf("%-6s %-22s %-14s %12s %8s %-12s%n",
                "ID", "Name", "Category", "Price (Rs.)", "Stock", "Warranty");
        System.out.println("-------------------------------------------------------------------------------");
        for (Product p : products) {
            System.out.printf("%-6d %-22s %-14s %12.2f %8d %-12s%n",
                    p.id, p.name, p.category, p.price, p.stock, p.warranty);
        }
    }

    private void addProduct() {
        int id = readInt("Enter product ID: ");
        if (findProduct(id) != null) {
            System.out.println("A product with this ID already exists.");
            return;
        }
        String name = readText("Enter product name: ");
        String category = readText("Enter category: ");
        double price = readDouble("Enter price: Rs. ");
        int stock = readInt("Enter stock: ");
        if (stock < 0) {
            System.out.println("Stock cannot be negative.");
            return;
        }
        String warranty = readText("Enter warranty period: ");
        products.add(new Product(id, name, category, price, stock, warranty));
        System.out.println("Product added successfully.");
    }

    private void editProduct() {
        int id = readInt("Enter product ID to edit: ");
        Product p = findProduct(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.println("Leave a field blank to keep its current value.");
        System.out.println("Current name: " + p.name);
        System.out.print("New name: ");
        String name = sc.nextLine().trim();
        System.out.println("Current category: " + p.category);
        System.out.print("New category: ");
        String category = sc.nextLine().trim();
        System.out.println("Current price: " + p.price);
        double price = readOptionalDouble("New price (blank to keep): ", p.price);
        System.out.println("Current stock: " + p.stock);
        int stock = readOptionalInt("New stock (blank to keep): ", p.stock);
        System.out.println("Current warranty: " + p.warranty);
        System.out.print("New warranty (blank to keep): ");
        String warranty = sc.nextLine().trim();

        if (!name.isEmpty()) p.name = name;
        if (!category.isEmpty()) p.category = category;
        p.price = price;
        p.stock = stock;
        if (!warranty.isEmpty()) p.warranty = warranty;
        System.out.println("Product updated successfully.");
    }

    private double readOptionalDouble(String prompt, double current) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (value.isEmpty()) return current;
            try {
                double number = Double.parseDouble(value);
                if (number >= 0) return number;
                System.out.println("Value cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }

    private int readOptionalInt(String prompt, int current) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (value.isEmpty()) return current;
            try {
                int number = Integer.parseInt(value);
                if (number >= 0) return number;
                System.out.println("Value cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid whole number.");
            }
        }
    }

    private void deleteProduct() {
        int id = readInt("Enter product ID to delete: ");
        Product p = findProduct(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        products.remove(p);
        System.out.println("Product deleted successfully.");
    }

    private void searchProduct() {
        String name = readText("Enter product name to search: ");
        for (Product p : products) {
            if (p.name.equalsIgnoreCase(name)) {
                System.out.println("\\nProduct Found");
                System.out.println("ID       : " + p.id);
                System.out.println("Name     : " + p.name);
                System.out.println("Category : " + p.category);
                System.out.printf("Price    : Rs. %.2f%n", p.price);
                System.out.println("Stock    : " + p.stock);
                System.out.println("Warranty : " + p.warranty);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    // ================= CUSTOMER MANAGEMENT =================
    private void customerMenu() {
        while (true) {
            System.out.println("\\n---------- CUSTOMER MANAGEMENT ----------");
            System.out.println("1. Display Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. Edit Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back to Main Menu");
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: displayCustomers(); break;
                case 2: addCustomer(); break;
                case 3: editCustomer(); break;
                case 4: deleteCustomer(); break;
                case 5: return;
                default: System.out.println("Invalid choice. Choose 1-5.");
            }
        }
    }

    private void displayCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }
        System.out.printf("%-8s %-25s %-16s%n", "ID", "Name", "Phone");
        System.out.println("--------------------------------------------------");
        for (Customer c : customers) {
            System.out.printf("%-8d %-25s %-16s%n", c.id, c.name, c.phone);
        }
    }

    private void addCustomer() {
        int id = readInt("Enter customer ID: ");
        if (findCustomer(id) != null) {
            System.out.println("A customer with this ID already exists.");
            return;
        }
        String name = readText("Enter customer name: ");
        String phone = readText("Enter phone number: ");
        customers.add(new Customer(id, name, phone));
        System.out.println("Customer added successfully.");
    }

    private void editCustomer() {
        int id = readInt("Enter customer ID to edit: ");
        Customer c = findCustomer(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("Current name: " + c.name);
        System.out.print("New name (blank to keep): ");
        String name = sc.nextLine().trim();
        System.out.println("Current phone: " + c.phone);
        System.out.print("New phone (blank to keep): ");
        String phone = sc.nextLine().trim();
        if (!name.isEmpty()) c.name = name;
        if (!phone.isEmpty()) c.phone = phone;
        System.out.println("Customer updated successfully.");
    }

    private void deleteCustomer() {
        int id = readInt("Enter customer ID to delete: ");
        Customer c = findCustomer(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        customers.remove(c);
        System.out.println("Customer deleted successfully.");
    }

    // ================= SALES =================
    private void salesMenu() {
        int customerId = readInt("Enter customer ID: ");
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found. Add the customer first.");
            return;
        }

        ArrayList<CartItem> cart = new ArrayList<>();
        while (true) {
            System.out.println("\\n---------- SALES / CART ----------");
            displayProducts();
            System.out.println("1. Add item to cart");
            System.out.println("2. View cart");
            System.out.println("3. Remove item from cart");
            System.out.println("4. Clear cart");
            System.out.println("5. Generate bill");
            System.out.println("6. Cancel sale");
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: addToCart(cart); break;
                case 2: displayCart(cart); break;
                case 3: removeFromCart(cart); break;
                case 4:
                    cart.clear();
                    System.out.println("Cart cleared.");
                    break;
                case 5:
                    if (cart.isEmpty()) {
                        System.out.println("Cart is empty.");
                    } else {
                        generateBill(customer, cart);
                        return;
                    }
                    break;
                case 6:
                    System.out.println("Sale cancelled.");
                    return;
                default:
                    System.out.println("Invalid choice. Choose 1-6.");
            }
        }
    }

    private void addToCart(ArrayList<CartItem> cart) {
        int productId = readInt("Enter product ID: ");
        Product p = findProduct(productId);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        int qty = readInt("Enter quantity: ");
        if (qty <= 0) {
            System.out.println("Quantity must be at least 1.");
            return;
        }

        CartItem existing = null;
        for (CartItem item : cart) {
            if (item.product.id == productId) {
                existing = item;
                break;
            }
        }
        int alreadyInCart = existing == null ? 0 : existing.qty;
        if (alreadyInCart + qty > p.stock) {
            System.out.println("Insufficient stock. Available: " + p.stock
                    + ", already in cart: " + alreadyInCart);
            return;
        }
        if (existing == null) cart.add(new CartItem(p, qty));
        else existing.qty += qty;
        System.out.println("Item added to cart.");
    }

    private void displayCart(ArrayList<CartItem> cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        double total = 0;
        System.out.printf("%-8s %-22s %12s %6s %14s%n",
                "ID", "Name", "Price", "Qty", "Subtotal");
        System.out.println("--------------------------------------------------------------------");
        for (CartItem item : cart) {
            System.out.printf("%-8d %-22s %12.2f %6d %14.2f%n",
                    item.product.id, item.product.name, item.product.price,
                    item.qty, item.subtotal());
            total += item.subtotal();
        }
        System.out.printf("TOTAL: Rs. %.2f%n", total);
    }

    private void removeFromCart(ArrayList<CartItem> cart) {
        int id = readInt("Enter product ID to remove from cart: ");
        for (CartItem item : cart) {
            if (item.product.id == id) {
                cart.remove(item);
                System.out.println("Item removed from cart.");
                return;
            }
        }
        System.out.println("That product is not in the cart.");
    }

    private void generateBill(Customer customer, ArrayList<CartItem> cart) {
        for (CartItem item : cart) {
            if (item.qty > item.product.stock) {
                System.out.println("Insufficient stock for " + item.product.name + ".");
                return;
            }
        }

        System.out.println("\\n==================== BILL ====================");
        System.out.println("Customer : " + customer.name);
        System.out.println("Phone    : " + customer.phone);
        System.out.println("------------------------------------------------");
        System.out.printf("%-22s %6s %12s %14s%n", "Item", "Qty", "Price", "Subtotal");
        System.out.println("------------------------------------------------");
        double total = 0;
        for (CartItem item : cart) {
            double subtotal = item.subtotal();
            total += subtotal;
            item.product.stock -= item.qty;
            System.out.printf("%-22s %6d %12.2f %14.2f%n",
                    item.product.name, item.qty, item.product.price, subtotal);
        }
        System.out.println("------------------------------------------------");
        System.out.printf("TOTAL (Rs.): %.2f%n", total);
        System.out.println("Thank you! Visit again.");
        System.out.println("================================================");
    }

    // ================= INVENTORY =================
    private void showInventory() {
        System.out.println("\\n========== INVENTORY ==========");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        for (Product p : products) {
            System.out.printf("%d | %s | Stock: %d", p.id, p.name, p.stock);
            if (p.stock <= 3) System.out.print("  ** LOW STOCK **");
            System.out.println();
        }
    }

    // ================= WARRANTY =================
    private void showWarranty() {
        int id = readInt("Enter product ID: ");
        Product p = findProduct(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.println("Product : " + p.name);
        System.out.println("Warranty: " + p.warranty);
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        new ElectronicShop().run();
    }
}
