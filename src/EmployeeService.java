import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

class EmployeeService {
    private EmployeeDAO employeeDAO;
    
    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }
    
    public void initializeDatabase() throws SQLException {
        employeeDAO.createTable();
    }
    
    public boolean addEmployee(String name, String email, String department, double salary) {
        try {
            if (name == null || name.trim().isEmpty()) {
                System.err.println("Error: Employee name cannot be empty.");
                return false;
            }
            if (email == null || !email.contains("@")) {
                System.err.println("Error: Invalid email format.");
                return false;
            }
            if (salary < 0) {
                System.err.println("Error: Salary cannot be negative.");
                return false;
            }
            
            Employee employee = new Employee(name.trim(), email.trim(), department.trim(), salary);
            return employeeDAO.addEmployee(employee);
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Employee> getAllEmployees() {
        try {
            return employeeDAO.getAllEmployees();
        } catch (SQLException e) {
            System.err.println("Failed to retrieve employees.");
            return new ArrayList<>();
        }
    }
    
    public Employee getEmployee(int id) {
        try {
            return employeeDAO.getEmployeeById(id);
        } catch (SQLException e) {
            System.err.println("Failed to retrieve employee with ID: " + id);
            return null;
        }
    }
    
    public boolean updateEmployee(int id, String name, String email, String department, double salary) {
        try {
            // Basic validation
            if (name == null || name.trim().isEmpty()) {
                System.err.println("Error: Employee name cannot be empty.");
                return false;
            }
            if (email == null || !email.contains("@")) {
                System.err.println("Error: Invalid email format.");
                return false;
            }
            if (salary < 0) {
                System.err.println("Error: Salary cannot be negative.");
                return false;
            }
            
            Employee employee = new Employee(id, name.trim(), email.trim(), department.trim(), salary);
            return employeeDAO.updateEmployee(employee);
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean deleteEmployee(int id) {
        try {
            return employeeDAO.deleteEmployee(id);
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Employee> getEmployeesByDepartment(String department) {
        try {
            return employeeDAO.getEmployeesByDepartment(department);
        } catch (SQLException e) {
            System.err.println("Failed to retrieve employees by department.");
            return new ArrayList<>();
        }
    }
}