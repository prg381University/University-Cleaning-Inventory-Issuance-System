package dao;

import model.Material;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    public void addMaterial(Material material) {
        String sql = "INSERT INTO Materials (name, quantity, reorderLevel) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, material.getName());
            pstmt.setInt(2, material.getQuantity());
            pstmt.setInt(3, material.getReorderLevel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMaterial(Material material) {
        String sql = "UPDATE Materials SET name = ?, quantity = ?, reorderLevel = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, material.getName());
            pstmt.setInt(2, material.getQuantity());
            pstmt.setInt(3, material.getReorderLevel());
            pstmt.setInt(4, material.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMaterial(int id) {
        String sql = "DELETE FROM Materials WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Material getMaterial(int id) {
        Material material = null;
        String sql = "SELECT * FROM Materials WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    material = new Material(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getInt("reorderLevel")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return material;
    }

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getInt("reorderLevel")
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    public List<Material> getAllLowStock() {
        List<Material> lowStockList = new ArrayList<>();
        String query = "SELECT * FROM Materials WHERE quantity <= reorderLevel";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getInt("reorderLevel")
                );
                lowStockList.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lowStockList;
    }

    public List<Material> searchMaterials(String query) {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE LOWER(name) LIKE LOWER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    materials.add(new Material(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getInt("reorderLevel")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }
}
