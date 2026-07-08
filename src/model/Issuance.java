package model;

import java.sql.Timestamp;

// Represents one row from the issuances table.
public class Issuance {
    private int issuanceId;
    private int materialId;
    private int cleanerId;
    private int issuedBy;
    private int quantityIssued;
    private Timestamp issueDate;

    public Issuance() {
    }

    public Issuance(int issuanceId, int materialId, int cleanerId, int issuedBy, int quantityIssued, Timestamp issueDate) {
        this.issuanceId = issuanceId;
        this.materialId = materialId;
        this.cleanerId = cleanerId;
        this.issuedBy = issuedBy;
        this.quantityIssued = quantityIssued;
        this.issueDate = issueDate;
    }

    public int getIssuanceId() {
        return issuanceId;
    }

    public void setIssuanceId(int issuanceId) {
        this.issuanceId = issuanceId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(int cleanerId) {
        this.cleanerId = cleanerId;
    }

    public int getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(int issuedBy) {
        this.issuedBy = issuedBy;
    }

    public int getQuantityIssued() {
        return quantityIssued;
    }

    public void setQuantityIssued(int quantityIssued) {
        this.quantityIssued = quantityIssued;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }
}