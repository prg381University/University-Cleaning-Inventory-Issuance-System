package dao;

import model.Issuance;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssuanceDAO {

    // CREATE: Add a new issuance (and deduct stock)
    public void addIssuance(Issuance issuance) throws SQLException {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  // Start transaction
            
            // 1. Check if enough stock is available
            String checkSql = "SELECT quantity FROM Materials WHERE id = ?";
            ps1 = conn.prepareStatement(checkSql);
            ps1.setInt(1, issuance.getMaterialId());
            ResultSet rs = ps1.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Material not found!");
            }
            
            int currentQty = rs.getInt("quantity");
            if (currentQty < issuance.getQuantity()) {
                throw new SQLException("Not enough stock! Available: " + currentQty);
            }
            
            // 2. Deduct stock from Materials table
            String updateSql = "UPDATE Materials SET quantity = quantity - ? WHERE id = ?";
            ps2 = conn.prepareStatement(updateSql);
            ps2.setInt(1, issuance.getQuantity());
            ps2.setInt(2, issuance.getMaterialId());
            ps2.executeUpdate();
            
            // 3. Insert the issuance record
            String insertSql = "INSERT INTO Issuance (materialId, cleanerId, quantity, notes, issuedDate) VALUES (?, ?, ?, ?, CURRENT_DATE)";
            PreparedStatement ps3 = conn.prepareStatement(insertSql);
            ps3.setInt(1, issuance.getMaterialId());
            ps3.setInt(2, issuance.getCleanerId());
            ps3.setInt(3, issuance.getQuantity());
            ps3.setString(4, issuance.getNotes());
            ps3.executeUpdate();
            
            conn.commit();  // All succeeded, commit transaction
            
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();  // Rollback on error
            }
            throw e;
        } finally {
            if (ps1 != null) ps1.close();
            if (ps2 != null) ps2.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // READ: Get all issuances with material and cleaner names
    public List<Issuance> getAllIssuances() {
        List<Issuance> issuances = new ArrayList<>();
        String sql = "SELECT i.*, m.name as materialName, c.fullName as cleanerName " +
                     "FROM Issuance i " +
                     "JOIN Materials m ON i.materialId = m.id " +
                     "JOIN Cleaners c ON i.cleanerId = c.id " +
                     "ORDER BY i.issuedDate DESC";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                issuances.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issuances;
    }

    // READ: Get a single issuance by ID
    public Issuance getIssuance(int id) {
        String sql = "SELECT i.*, m.name as materialName, c.fullName as cleanerName " +
                     "FROM Issuance i " +
                     "JOIN Materials m ON i.materialId = m.id " +
                     "JOIN Cleaners c ON i.cleanerId = c.id " +
                     "WHERE i.id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // DELETE: Delete an issuance (and return stock)
    public void deleteIssuance(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Get the issuance details
            String selectSql = "SELECT materialId, quantity FROM Issuance WHERE id = ?";
            ps1 = conn.prepareStatement(selectSql);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();
            
            if (rs.next()) {
                int materialId = rs.getInt("materialId");
                int quantity = rs.getInt("quantity");
                
                // 2. Return stock to Materials table
                String updateSql = "UPDATE Materials SET quantity = quantity + ? WHERE id = ?";
                ps2 = conn.prepareStatement(updateSql);
                ps2.setInt(1, quantity);
                ps2.setInt(2, materialId);
                ps2.executeUpdate();
                
                // 3. Delete the issuance
                String deleteSql = "DELETE FROM Issuance WHERE id = ?";
                PreparedStatement ps3 = conn.prepareStatement(deleteSql);
                ps3.setInt(1, id);
                ps3.executeUpdate();
            }
            
            conn.commit();
            
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (ps1 != null) ps1.close();
            if (ps2 != null) ps2.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // SEARCH: Search issuances by material name or cleaner name
    public List<Issuance> searchIssuances(String query) {
        List<Issuance> issuances = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return getAllIssuances();
        }
        
        String sql = "SELECT i.*, m.name as materialName, c.fullName as cleanerName " +
                     "FROM Issuance i " +
                     "JOIN Materials m ON i.materialId = m.id " +
                     "JOIN Cleaners c ON i.cleanerId = c.id " +
                     "WHERE LOWER(m.name) LIKE ? OR LOWER(c.fullName) LIKE ? " +
                     "ORDER BY i.issuedDate DESC";
        
        String pattern = "%" + query.toLowerCase() + "%";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                issuances.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issuances;
    }

    // Helper: Map ResultSet to Issuance
    private Issuance map(ResultSet rs) throws SQLException {
        return new Issuance(
            rs.getInt("id"),
            rs.getInt("materialId"),
            rs.getString("materialName"),
            rs.getInt("cleanerId"),
            rs.getString("cleanerName"),
            rs.getInt("quantity"),
            rs.getString("issuedDate"),
            rs.getString("notes")
        );
    }
}