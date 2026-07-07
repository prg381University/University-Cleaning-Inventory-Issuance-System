package dao;

import model.Supplier;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Handles database operations (CRUD) for the suppliers table.
public class SupplierDAO {

    // Adds a new supplier.
    public void create(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO suppliers (name, contact_person, phone, email, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getPhone());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getAddress());

            stmt.executeUpdate();
        }
    }

    // Gets one supplier by id.
    public Supplier getById(int supplierId) throws SQLException {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // Gets all suppliers.
    public List<Supplier> getAll() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                suppliers.add(mapRow(rs));
            }
        }
        return suppliers;
    }

    // Updates an existing supplier.
    public void update(Supplier supplier) throws SQLException {
        String sql = "UPDATE suppliers SET name = ?, contact_person = ?, phone = ?, email = ?, address = ? WHERE supplier_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getPhone());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getAddress());
            stmt.setInt(6, supplier.getSupplierId());

            stmt.executeUpdate();
        }
    }

    // Deletes a supplier by id.
    public void delete(int supplierId) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplierId);
            stmt.executeUpdate();
        }
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