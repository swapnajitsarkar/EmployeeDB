import java.sql.*;
import java.util.Properties;

class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password"; // Change this to your MySQL password
    
    public static Connection getConnection() throws SQLException {
        try {
            // Register JDBC driver (optional for newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");            
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("useSSL", "false");
            props.setProperty("serverTimezone", "UTC");
            
            return DriverManager.getConnection(DB_URL, props);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    public static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { 
                System.err.println("Error closing ResultSet: " + e.getMessage()); 
            }
        }
        if (stmt != null) {
            try { stmt.close(); } catch (SQLException e) { 
                System.err.println("Error closing PreparedStatement: " + e.getMessage()); 
            }
        }
        closeConnection(conn);
    }
}