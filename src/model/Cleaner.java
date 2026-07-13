package model;

public class Cleaner {
    private int id;
    private String fullName;
    private String department;
    private String phone;
    private String email;

    // Constructor for adding (no ID)
    public Cleaner(String fullName, String department, String phone, String email) {
        this.fullName = fullName;
        this.department = department;
        this.phone = phone;
        this.email = email;
    }

    // Full constructor (for reading from DB)
    public Cleaner(int id, String fullName, String department, String phone, String email) {
        this.id = id;
        this.fullName = fullName;
        this.department = department;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}