package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Issuance;
import util.DBConnection;

// Handles database operations for the issuances table.
public class IssuanceDAO {

    // Creates an issuance and deducts stock from materials.
    public void issueMaterial(Issuance issuance) throws SQLException {
        Connection conn = DBConnection.getConnection();

        conn.setAutoCommit(false);

        try {
            // Check current stock for this material.
            String checkSql = "SELECT quantity FROM materials WHERE material_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, issuance.getMaterialId());
            ResultSet rs = checkStmt.executeQuery();

            int currentQty = 0;
            if (rs.next()) {
                currentQty = rs.getInt("quantity");
            }
            rs.close();
            checkStmt.close();

            // Check if there is enough stock available.
            if (issuance.getQuantityIssued() > currentQty) {
                conn.rollback();
                conn.close();
                throw new SQLException("Not enough stock available.");
            }

            // Insert the new issuance record.
            String insertSql = "INSERT INTO issuances (material_id, cleaner_id, issued_by, quantity_issued) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, issuance.getMaterialId());
            insertStmt.setInt(2, issuance.getCleanerId());
            insertStmt.setInt(3, issuance.getIssuedBy());
            insertStmt.setInt(4, issuance.getQuantityIssued());
            insertStmt.executeUpdate();
            insertStmt.close();

            // Subtract the issued quantity from the material's stock.
            String updateSql = "UPDATE materials SET quantity = quantity - ? WHERE material_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, issuance.getQuantityIssued());
            updateStmt.setInt(2, issuance.getMaterialId());
            updateStmt.executeUpdate();
            updateStmt.close();

            // Save both changes together.
            conn.commit();

        } catch (SQLException e) {
            // If anything went wrong, undo everything.
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}