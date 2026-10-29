# Electronic Shop Management System — Console Version

This is the terminal-based version of the Electronic Shop Management System. The Java Swing GUI has been removed.

## Features
- Product management: display, add, edit, delete, and search products
- Customer management: display, add, edit, and delete customers
- Sales: shopping cart, stock validation, and bill generation
- Inventory listing with low-stock warnings
- Warranty lookup by product ID
- Main menu and exit option

## Requirements
- Java JDK installed

## Compile and run
Open a terminal in this folder and run:

```bash
javac ElectronicShop.java
java ElectronicShop
```

## Data storage
Products and customers are held in `ArrayList` objects in memory. Data is reset when the application closes; no database or file persistence is implemented.
