package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Issuance;
import util.DBConnection;

public class IssuanceDAO {

    // this method adds a new issuance and automatically deducts stock
    // it uses a transaction so everything happens together or not at all
    public void addIssuance(Issuance issuance) throws SQLException {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
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
            
            String updateSql = "UPDATE Materials SET quantity = quantity - ? WHERE id = ?";
            ps2 = conn.prepareStatement(updateSql);
            ps2.setInt(1, issuance.getQuantity());
            ps2.setInt(2, issuance.getMaterialId());
            ps2.executeUpdate();
            
            String insertSql = "INSERT INTO Issuance (materialId, cleanerId, quantity, notes, issuedDate) VALUES (?, ?, ?, ?, CURRENT_DATE)";
            PreparedStatement ps3 = conn.prepareStatement(insertSql);
            ps3.setInt(1, issuance.getMaterialId());
            ps3.setInt(2, issuance.getCleanerId());
            ps3.setInt(3, issuance.getQuantity());
            ps3.setString(4, issuance.getNotes());
            ps3.executeUpdate();
            
            conn.commit();
            
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
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

    // gets all issuances with the material name and cleaner name joined
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

    // gets a single issuance by its id
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

    // deletes an issuance and returns the stock to materials
    public void deleteIssuance(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            String selectSql = "SELECT materialId, quantity FROM Issuance WHERE id = ?";
            ps1 = conn.prepareStatement(selectSql);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();
            
            if (rs.next()) {
                int materialId = rs.getInt("materialId");
                int quantity = rs.getInt("quantity");
                
                String updateSql = "UPDATE Materials SET quantity = quantity + ? WHERE id = ?";
                ps2 = conn.prepareStatement(updateSql);
                ps2.setInt(1, quantity);
                ps2.setInt(2, materialId);
                ps2.executeUpdate();
                
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

    // searches issuances by material name or cleaner name
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

    // this method issues stock with auto deduction and records who issued it
    public void issueStock(int materialId, int cleanerId, int quantity, String notes, int issuedBy) 
            throws SQLException {
        
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            String checkSql = "SELECT quantity, name FROM Materials WHERE id = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, materialId);
            rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Material not found!");
            }
            
            int currentQty = rs.getInt("quantity");
            String materialName = rs.getString("name");
            
            if (currentQty < quantity) {
                throw new SQLException("Not enough stock! Available: " + currentQty + ", Requested: " + quantity);
            }
            
            String updateSql = "UPDATE Materials SET quantity = quantity - ? WHERE id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, materialId);
            int rowsUpdated = updateStmt.executeUpdate();
            
            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update stock!");
            }
            
            String insertSql = "INSERT INTO Issuance (materialId, cleanerId, quantity, notes, issuedDate, issuedBy) " +
                              "VALUES (?, ?, ?, ?, CURRENT_DATE, ?)";
            insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, materialId);
            insertStmt.setInt(2, cleanerId);
            insertStmt.setInt(3, quantity);
            insertStmt.setString(4, notes);
            insertStmt.setInt(5, issuedBy);
            insertStmt.executeUpdate();
            
            conn.commit();
            System.out.println("Stock issued successfully: " + quantity + " x " + materialName);
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transaction rolled back: " + e.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (checkStmt != null) try { checkStmt.close(); } catch (SQLException e) {}
            if (updateStmt != null) try { updateStmt.close(); } catch (SQLException e) {}
            if (insertStmt != null) try { insertStmt.close(); } catch (SQLException e) {}
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // this method returns stock when an issuance is deleted
    public void returnStock(int issuanceId) throws SQLException {
        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            String selectSql = "SELECT materialId, quantity FROM Issuance WHERE id = ?";
            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, issuanceId);
            rs = selectStmt.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Issuance not found!");
            }
            
            int materialId = rs.getInt("materialId");
            int quantity = rs.getInt("quantity");
            
            String updateSql = "UPDATE Materials SET quantity = quantity + ? WHERE id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, materialId);
            updateStmt.executeUpdate();
            
            String deleteSql = "DELETE FROM Issuance WHERE id = ?";
            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, issuanceId);
            deleteStmt.executeUpdate();
            
            conn.commit();
            System.out.println("Stock returned successfully for issuance: " + issuanceId);
            
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (selectStmt != null) try { selectStmt.close(); } catch (SQLException e) {}
            if (updateStmt != null) try { updateStmt.close(); } catch (SQLException e) {}
            if (deleteStmt != null) try { deleteStmt.close(); } catch (SQLException e) {}
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // helper method that maps a database row to an issuance object
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