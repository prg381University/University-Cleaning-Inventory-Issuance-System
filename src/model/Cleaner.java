package model;

// Represents one row from the cleaners table.
public class Cleaner {
    private int cleanerId;
    private String name;
    private String department;
    private String contactNumber;

    public Cleaner() {
    }

    public Cleaner(int cleanerId, String name, String department, String contactNumber) {
        this.cleanerId = cleanerId;
        this.name = name;
        this.department = department;
        this.contactNumber = contactNumber;
    }

    public int getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(int cleanerId) {
        this.cleanerId = cleanerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
