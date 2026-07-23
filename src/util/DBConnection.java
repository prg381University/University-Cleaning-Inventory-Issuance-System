package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String DB_URL = "jdbc:derby:cleaningInventoryDB;create=true";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Derby Driver not found! Check your WEB-INF/lib folder.");
            e.printStackTrace();
        }

        Connection conn = DriverManager.getConnection(DB_URL);
        createTableIfNotExists(conn);
        return conn;
    }

    private static void createTableIfNotExists(Connection conn) {
        String materialsSql = "CREATE TABLE Materials ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
                + "name VARCHAR(100), "
                + "quantity INT, "
                + "reorderLevel INT)";

        String usersSql = "CREATE TABLE users ("
                + "user_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
                + "username VARCHAR(100) UNIQUE NOT NULL, "
                + "password VARCHAR(255) NOT NULL, "
                + "email VARCHAR(100) UNIQUE NOT NULL, "
                + "full_name VARCHAR(100), "
                + "role VARCHAR(20) NOT NULL)";

        String suppliersSql = "CREATE TABLE Suppliers ("
                + "supplierId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
                + "supplierName VARCHAR(100), "
                + "contactPerson VARCHAR(100), "
                + "phone VARCHAR(20), "
                + "email VARCHAR(100), "
                + "address VARCHAR(255))";

        String cleanersSql = "CREATE TABLE Cleaners ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
                + "fullName VARCHAR(100), "
                + "department VARCHAR(50), "
                + "phone VARCHAR(20), "
                + "email VARCHAR(100))";

        String issuancesSql = "CREATE TABLE Issuance ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
                + "materialId INT, "
                + "cleanerId INT, "
                + "quantity INT, "
                + "notes VARCHAR(255), "
                + "issuedDate DATE, "
                + "issuedBy INT)";

        try (Statement stmt = conn.createStatement()) {
            String[] tables = {materialsSql, usersSql, suppliersSql, cleanersSql, issuancesSql};

            for (String sql : tables) {
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    if (!e.getSQLState().equals("X0Y32")) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Database tables checked/created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
// pain