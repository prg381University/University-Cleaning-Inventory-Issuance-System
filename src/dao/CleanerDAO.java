package dao;

import model.Cleaner;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CleanerDAO {

    // CREATE: Add a new cleaner
    public void addCleaner(Cleaner cleaner) {
        String sql = "INSERT INTO Cleaners (fullName, department, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cleaner.getFullName());
            pstmt.setString(2, cleaner.getDepartment());
            pstmt.setString(3, cleaner.getPhone());
            pstmt.setString(4, cleaner.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ: Get all cleaners
    public List<Cleaner> getAllCleaners() {
        List<Cleaner> cleaners = new ArrayList<>();
        String sql = "SELECT * FROM Cleaners ORDER BY fullName";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cleaner cleaner = new Cleaner(
                        rs.getInt("id"),
                        rs.getString("fullName"),
                        rs.getString("department"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
                cleaners.add(cleaner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cleaners;
    }

    // READ: Get a single cleaner by ID
    public Cleaner getCleaner(int id) {
        Cleaner cleaner = null;
        String sql = "SELECT * FROM Cleaners WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cleaner = new Cleaner(
                            rs.getInt("id"),
                            rs.getString("fullName"),
                            rs.getString("department"),
                            rs.getString("phone"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cleaner;
    }

    // UPDATE: Update an existing cleaner
    public void updateCleaner(Cleaner cleaner) {
        String sql = "UPDATE Cleaners SET fullName = ?, department = ?, phone = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cleaner.getFullName());
            pstmt.setString(2, cleaner.getDepartment());
            pstmt.setString(3, cleaner.getPhone());
            pstmt.setString(4, cleaner.getEmail());
            pstmt.setInt(5, cleaner.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE: Delete a cleaner by ID
    public void deleteCleaner(int id) {
        String sql = "DELETE FROM Cleaners WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SEARCH: Search cleaners by name or department
    public List<Cleaner> searchCleaners(String query) {
        List<Cleaner> cleaners = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return getAllCleaners();
        }
        String sql = "SELECT * FROM Cleaners WHERE LOWER(fullName) LIKE LOWER(?) OR LOWER(department) LIKE LOWER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String pattern = "%" + query + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cleaners.add(new Cleaner(
                            rs.getInt("id"),
                            rs.getString("fullName"),
                            rs.getString("department"),
                            rs.getString("phone"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cleaners;
    }
}