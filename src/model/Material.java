package model;

// Represents one row from the materials table.
public class Material {
    private int materialId;
    private String name;
    private String description;
    private int quantity;
    private int reorderLevel;
    private String unit;
    private int supplierId;

    public Material() {
    }

    public Material(int materialId, String name, String description, int quantity, int reorderLevel, String unit, int supplierId) {
        this.materialId = materialId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.unit = unit;
        this.supplierId = supplierId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
