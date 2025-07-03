# 👨‍💼 Employee Database Management System

A comprehensive **Java application** demonstrating **JDBC** connectivity with **MySQL/PostgreSQL** for performing full **CRUD operations** on an employee database.

---

## 📋 Overview

This project provides a complete employee management system with capabilities to:

- Add new employees  
- View all employees  
- View specific employee by ID  
- Update employee information  
- Delete employees  
- Search employees by department  
- Secure database operations with `PreparedStatement`  

---

## 🛠️ Technologies Used

- **Java 8+** – Core programming language  
- **JDBC** – Database connectivity  
- **MySQL / PostgreSQL** – Database systems  
- **PreparedStatement** – Secure SQL execution  
- **Transaction Management** – Ensures data integrity  

---

## 🎯 Features

### ➕ Add Employee
- Input validation for all fields  
- Duplicate email prevention  
- Transaction management  

### 📋 View All Employees
- Displays formatted table  
- Shows employee count summary  
- Sorted by ID  

### 🔍 View Employee by ID
- Individual employee details  
- Handles non-existent IDs gracefully  

### ✏️ Update Employee
- Update specific fields  
- Preserves current values if unchanged  
- Includes input validation and error handling  

### ❌ Delete Employee
- Confirmation before deletion  
- Safe transaction-based deletion  

### 🏢 Search by Department
- Filters employees by department  
- Results sorted alphabetically by name  

---

## 💡 Key JDBC Concepts Demonstrated

### 🔌 Connection Management
```java
public static Connection getConnection() throws SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
}
