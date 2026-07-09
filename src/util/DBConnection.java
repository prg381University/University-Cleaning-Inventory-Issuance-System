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
        String sql = "CREATE TABLE Materials ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
                + "name VARCHAR(100), "
                + "quantity INT, "
                + "reorderLevel INT)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Materials table successfully created.");
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) {
                e.printStackTrace();
            }
        }
    }
}