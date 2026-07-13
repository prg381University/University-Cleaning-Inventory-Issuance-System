package model;

public class Issuance {
    private int id;
    private int materialId;
    private String materialName;  // For display
    private int cleanerId;
    private String cleanerName;   // For display
    private int quantity;
    private String issuedDate;
    private String notes;

    // Constructor for adding (no ID)
    public Issuance(int materialId, int cleanerId, int quantity, String notes) {
        this.materialId = materialId;
        this.cleanerId = cleanerId;
        this.quantity = quantity;
        this.notes = notes;
    }

    // Full constructor (for reading from DB)
    public Issuance(int id, int materialId, String materialName, int cleanerId, String cleanerName, int quantity, String issuedDate, String notes) {
        this.id = id;
        this.materialId = materialId;
        this.materialName = materialName;
        this.cleanerId = cleanerId;
        this.cleanerName = cleanerName;
        this.quantity = quantity;
        this.issuedDate = issuedDate;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public String getMaterialName() { return materialName; }
    public void setMaterialName(String materialName) { this.materialName = materialName; }

    public int getCleanerId() { return cleanerId; }
    public void setCleanerId(int cleanerId) { this.cleanerId = cleanerId; }

    public String getCleanerName() { return cleanerName; }
    public void setCleanerName(String cleanerName) { this.cleanerName = cleanerName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getIssuedDate() { return issuedDate; }
    public void setIssuedDate(String issuedDate) { this.issuedDate = issuedDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}