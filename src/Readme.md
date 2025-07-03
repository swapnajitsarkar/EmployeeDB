Employee Database Management System
A comprehensive Java application demonstrating JDBC connectivity with MySQL/PostgreSQL for performing CRUD operations on an employee database.
📋 Overview
This application provides a complete employee management system with the following capabilities:

Add new employees
View all employees
View specific employee by ID
Update employee information
Delete employees
Search employees by department
Secure database operations with PreparedStatement

🛠️ Technologies Used

Java 8+ - Core programming language
JDBC - Database connectivity
MySQL/PostgreSQL - Database systems
PreparedStatement - Secure SQL execution
Transaction Management - Data integrity

🎯 Features
1. Add Employee

Input validation for all fields
Duplicate email prevention
Transaction management

2. View All Employees

Formatted table display
Employee count summary
Sorted by ID

3. View Employee by ID

Individual employee details
Error handling for non-existent IDs

4. Update Employee

Selective field updates
Current value preservation
Validation and error handling

5. Delete Employee

Confirmation prompt
Safe deletion with transaction management

6. Search by Department

Filter employees by department
Sorted results by name

💡 Key JDBC Concepts Demonstrated
1. Connection Management
javapublic static Connection getConnection() throws SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
}
2. PreparedStatement Usage
javaString insertSQL = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";
PreparedStatement stmt = conn.prepareStatement(insertSQL);
stmt.setString(1, employee.getName());
stmt.setString(2, employee.getEmail());
// ... set other parameters
3. Transaction Management
javaconn.setAutoCommit(false);
// ... perform operations
conn.commit(); // or conn.rollback() on error
4. Resource Management
javapublic static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
    // Proper cleanup of all resources
}
5. SQL Injection Prevention

Uses PreparedStatement instead of Statement
Parameterized queries
Input validation

📝 Database Schema
sqlCREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

🔧 Configuration
MySQL Configuration (default)
javaprivate static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";