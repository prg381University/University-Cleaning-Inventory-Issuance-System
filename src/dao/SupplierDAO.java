package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;
import util.DBConnection;

// Handles database operations (CRUD) for the suppliers table.
public class SupplierDAO {

    // Adds a new supplier.
    public void create(Supplier supplier) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO suppliers (name, contact_person, phone, email, address) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, supplier.getName());
        stmt.setString(2, supplier.getContactPerson());
        stmt.setString(3, supplier.getPhone());
        stmt.setString(4, supplier.getEmail());
        stmt.setString(5, supplier.getAddress());

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    // Gets one supplier by id.
    public Supplier getById(int supplierId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, supplierId);

        ResultSet rs = stmt.executeQuery();

        Supplier supplier = null;
        if (rs.next()) {
            supplier = mapRow(rs);
        }

        rs.close();
        stmt.close();
        conn.close();

        return supplier;
    }

    // Gets all suppliers.
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM suppliers";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            suppliers.add(mapRow(rs));
        }

        rs.close();
        stmt.close();
        conn.close();

        return suppliers;
    }

    // Updates an existing supplier.
    public void update(Supplier supplier) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "UPDATE suppliers SET name = ?, contact_person = ?, phone = ?, email = ?, address = ? WHERE supplier_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, supplier.getName());
        stmt.setString(2, supplier.getContactPerson());
        stmt.setString(3, supplier.getPhone());
        stmt.setString(4, supplier.getEmail());
        stmt.setString(5, supplier.getAddress());
        stmt.setInt(6, supplier.getSupplierId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    // Deletes a supplier by id.
    public void delete(int supplierId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, supplierId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    // Turns one database row into a Supplier object.
    private Supplier mapRow(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(rs.getInt("supplier_id"));
        supplier.setName(rs.getString("name"));
        supplier.setContactPerson(rs.getString("contact_person"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setEmail(rs.getString("email"));
        supplier.setAddress(rs.getString("address"));
        return supplier;
    }
}