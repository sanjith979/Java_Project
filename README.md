# Electronic Shop Management System – Java

## Classes
- `Product`: Stores product ID, name, category, price, stock, and warranty.
- `Customer`: Stores customer ID, name, and phone number.
- `ElectronicShop`: Main class that extends `JFrame` and manages the GUI.

## Features
1. **Product Management**
   - Add products
   - Delete selected products
   - Search products by name
   - Display products in a `JTable`

2. **Customer Management**
   - Add customers
   - Delete selected customers
   - Display customers in a `JTable`

3. **Sales**
   - Enter customer ID, product ID, and quantity
   - Validate customer and product IDs
   - Check product stock availability
   - Calculate total bill
   - Reduce stock after a successful sale

4. **Inventory**
   - Display product IDs, names, and stock
   - Show a low-stock warning when stock is 3 or less

5. **Warranty**
   - Search for a product using its ID
   - Display the product name and warranty period

6. **Exit**
   - Closes the application

## Technologies Used
- Java
- Java Swing
- `JFrame`
- `JPanel`
- `JButton`
- `JLabel`
- `JTextField`
- `JTable`
- `DefaultTableModel`
- `JOptionPane`
- `JScrollPane`
- `GridLayout`
- `BorderLayout`
- `ArrayList`

## Important Java Concepts
- Classes and objects
- Constructors
- Inheritance (`ElectronicShop extends JFrame`)
- Encapsulation concepts through separate data classes (fields are package-private, not private)
- ArrayList for storing products and customers
- Event handling using `addActionListener()`
- Lambda expressions
- Exception handling using `try-catch`
- Loops (`for`)
- Conditional statements (`if`)
- Searching using loops
- Deleting elements using `removeIf()`
- String comparison using `equalsIgnoreCase()`
- GUI table updates using `DefaultTableModel`

## Sample Products
| ID | Name | Category | Price | Stock | Warranty |
|---|---|---|---:|---:|---|
| 101 | Samsung S25 | Mobile | ₹79,999 | 10 | 1 Year |
| 102 | HP Victus | Laptop | ₹65,000 | 5 | 2 Years |
| 103 | Sony Bravia | TV | ₹55,000 | 7 | 2 Years |
| 104 | Sony Headphones | Audio | ₹5,000 | 15 | 1 Year |

## Important Methods
- `ElectronicShop()` – Initializes the application and sample products.
- `showDashboard()` – Displays the main menu.
- `productWindow()` – Manages products.
- `refreshProducts()` – Refreshes the product table.
- `customerWindow()` – Manages customers.
- `refreshCustomers()` – Refreshes the customer table.
- `salesWindow()` – Generates bills and updates stock.
- `inventoryWindow()` – Displays inventory and low-stock warnings.
- `warrantyWindow()` – Displays warranty information.
- `main()` – Starts the application.

## Billing Formula
Total Bill = Product Price × Quantity

Example:
- Product price = ₹5,000
- Quantity = 2
- Total = ₹10,000

## Important Validation Checks
- Invalid input displays an error message.
- Deleting without selecting a row displays a warning.
- Searching for a nonexistent product displays "Product Not Found!"
- Selling a nonexistent product displays "Product not found!"
- Selling to a nonexistent customer displays "Customer not found!"
- Selling more than the available stock displays "Insufficient stock!"

## Data Storage
- Products and customers are stored in `ArrayList`.
- Data is stored in memory only.
- No database or file-based persistence is implemented.
- Data is lost when the application closes.
