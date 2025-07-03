import java.util.List;
import java.util.Scanner;

public class Main {
    private static EmployeeService employeeService = new EmployeeService();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Employee Database Management System ===");
        
        try {
            // Initialize database
            employeeService.initializeDatabase();
            
            // Main application loop
            while (true) {
                showMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        viewEmployeeById();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 5:
                        deleteEmployee();
                        break;
                    case 6:
                        searchByDepartment();
                        break;
                    case 7:
                        System.out.println("Thank you for using Employee Database Management System!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static void showMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. View Employee by ID");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Search by Department");
        System.out.println("7. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-7): ");
    }
    
    private static int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter salary: ");
        try {
            double salary = Double.parseDouble(scanner.nextLine());
            
            if (employeeService.addEmployee(name, email, department, salary)) {
                System.out.println("✓ Employee added successfully!");
            } else {
                System.out.println("✗ Failed to add employee.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid salary format. Please enter a valid number.");
        }
    }
    
    private static void viewAllEmployees() {
        System.out.println("\n--- All Employees ---");
        
        List<Employee> employees = employeeService.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in the database.");
        } else {
            System.out.println(String.format("%-5s %-20s %-25s %-15s %-10s", 
                              "ID", "Name", "Email", "Department", "Salary"));
            System.out.println("-".repeat(80));
            
            for (Employee emp : employees) {
                System.out.println(String.format("%-5d %-20s %-25s %-15s $%-9.2f", 
                                  emp.getId(), emp.getName(), emp.getEmail(), 
                                  emp.getDepartment(), emp.getSalary()));
            }
            System.out.println("-".repeat(80));
            System.out.println("Total employees: " + employees.size());
        }
    }
    
    private static void viewEmployeeById() {
        System.out.println("\n--- View Employee by ID ---");
        
        System.out.print("Enter employee ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            Employee employee = employeeService.getEmployee(id);
            
            if (employee != null) {
                System.out.println("\n--- Employee Details ---");
                System.out.println("ID: " + employee.getId());
                System.out.println("Name: " + employee.getName());
                System.out.println("Email: " + employee.getEmail());
                System.out.println("Department: " + employee.getDepartment());
                System.out.println("Salary: $" + String.format("%.2f", employee.getSalary()));
            } else {
                System.out.println("✗ Employee not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format. Please enter a valid number.");
        }
    }
    
    private static void updateEmployee() {
        System.out.println("\n--- Update Employee ---");
        
        System.out.print("Enter employee ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            Employee existing = employeeService.getEmployee(id);
            if (existing == null) {
                System.out.println("✗ Employee not found with ID: " + id);
                return;
            }
            
            System.out.println("\nCurrent details:");
            System.out.println("Name: " + existing.getName());
            System.out.println("Email: " + existing.getEmail());
            System.out.println("Department: " + existing.getDepartment());
            System.out.println("Salary: $" + String.format("%.2f", existing.getSalary()));
            
            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("Enter name [" + existing.getName() + "]: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) name = existing.getName();
            
            System.out.print("Enter email [" + existing.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (email.trim().isEmpty()) email = existing.getEmail();
            
            System.out.print("Enter department [" + existing.getDepartment() + "]: ");
            String department = scanner.nextLine();
            if (department.trim().isEmpty()) department = existing.getDepartment();
            
            System.out.print("Enter salary [" + existing.getSalary() + "]: ");
            String salaryStr = scanner.nextLine();
            double salary = existing.getSalary();
            
            if (!salaryStr.trim().isEmpty()) {
                try {
                    salary = Double.parseDouble(salaryStr);
                } catch (NumberFormatException e) {
                    System.out.println("✗ Invalid salary format. Using current value.");
                }
            }
            
            if (employeeService.updateEmployee(id, name, email, department, salary)) {
                System.out.println("✓ Employee updated successfully!");
            } else {
                System.out.println("✗ Failed to update employee.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format. Please enter a valid number.");
        }
    }
    
    private static void deleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        
        System.out.print("Enter employee ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            Employee existing = employeeService.getEmployee(id);
            if (existing == null) {
                System.out.println("✗ Employee not found with ID: " + id);
                return;
            }
            
            System.out.println("\nEmployee to delete:");
            System.out.println("Name: " + existing.getName());
            System.out.println("Email: " + existing.getEmail());
            System.out.println("Department: " + existing.getDepartment());
            
            System.out.print("Are you sure you want to delete this employee? (y/N): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                if (employeeService.deleteEmployee(id)) {
                    System.out.println("✓ Employee deleted successfully!");
                } else {
                    System.out.println("✗ Failed to delete employee.");
                }
            } else {
                System.out.println("Delete operation cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format. Please enter a valid number.");
        }
    }
    
    private static void searchByDepartment() {
        System.out.println("\n--- Search by Department ---");
        
        System.out.print("Enter department name: ");
        String department = scanner.nextLine();
        
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
        } else {
            System.out.println("\n--- Employees in " + department + " ---");
            System.out.println(String.format("%-5s %-20s %-25s %-10s", 
                              "ID", "Name", "Email", "Salary"));
            System.out.println("-".repeat(65));
            
            for (Employee emp : employees) {
                System.out.println(String.format("%-5d %-20s %-25s $%-9.2f", 
                                  emp.getId(), emp.getName(), emp.getEmail(), emp.getSalary()));
            }
            System.out.println("-".repeat(65));
            System.out.println("Total employees in " + department + ": " + employees.size());
        }
    }
}