import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class EmployeeDAO {
    
    public void createTable() throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS employees (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                department VARCHAR(50) NOT NULL,
                salary DECIMAL(10,2) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
        """;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(createTableSQL);
            stmt.execute();
            System.out.println("Table 'employees' created successfully or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            throw e;
        } finally {
            DatabaseConnection.closeResources(conn, stmt, null);
        }
    }
    
    public boolean addEmployee(Employee employee) throws SQLException {
        String insertSQL = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getDepartment());
            stmt.setDouble(4, employee.getSalary());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                System.out.println("Employee added successfully!");
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("Error: Email already exists in the database.");
            } else {
                System.err.println("Error adding employee: " + e.getMessage());
            }
            throw e;
        } finally {
            DatabaseConnection.closeResources(conn, stmt, null);
        }
    }
    
    public List<Employee> getAllEmployees() throws SQLException {
        String selectSQL = "SELECT * FROM employees ORDER BY id";
        List<Employee> employees = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(selectSQL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            throw e;
        } finally {
            DatabaseConnection.closeResources(conn, stmt, rs);
        }
        
        return employees;
    }
    
    public Employee getEmployeeById(int id) throws SQLException {
        String selectSQL = "SELECT * FROM employees WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(selectSQL);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
            throw e;
        } finally {
            DatabaseConnection.closeResources(conn, stmt, rs);
        }
        
        return null;
    }
    
    public boolean updateEmployee(Employee employee) throws SQLException {
        String updateSQL = "UPDATE employees SET name = ?, email = ?, department = ?, salary = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(updateSQL);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getDepartment());
            stmt.setDouble(4, employee.getSalary());
            stmt.setInt(5, employee.getId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                System.out.println("Employee updated successfully!");
                return true;
            } else {
                conn.rollback(); // Rollback on failure
                System.out.println("No employee found with ID: " + employee.getId());
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("Error: Email already exists in the database.");
            } else {
                System.err.println("Error updating employee: " + e.getMessage());
            }
            throw e;
        } finally {
            DatabaseConnection.closeResources(conn, stmt, null);
        }
    }
    
    public boolean deleteEmployee(int id) throws SQLException {
        String deleteSQL = "DELETE FROM employees WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(deleteSQL);
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit();
                System.out.println("Employee deleted successfully!");
                return true;
            } else {
                conn.rollback();
                System.out.println("No employee found with ID: " + id);
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Error deleting employee: " + e.getMessage());
            throw e;
        } finally {
            DatabaseConnection.closeResources(conn, stmt, null);
        }
    }
    
    public List<Employee> getEmployeesByDepartment(String department) throws SQLException {
        String selectSQL = "SELECT * FROM employees WHERE department = ? ORDER BY name";
        List<Employee> employees = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(selectSQL);
            stmt.setString(1, department);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees by department: " + e.getMessage());
            throw e;
        } finally {
            DatabaseConnection.closeResources(conn, stmt, rs);
        }
        
        return employees;
    }
}