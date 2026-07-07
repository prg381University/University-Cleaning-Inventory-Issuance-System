package util; // Tells Java this class belongs to the util package(needed for structure line up)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Handles the connection to the Derby database
// DAOs uses this instead of opening their own connections
public class DBConnection {

    // Points to the local embedded Derby database folder
    private static final String DB_URL = "jdbc:derby:cleaningInventoryDB";

    // Returns a new connection. Called by DAO classes
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}