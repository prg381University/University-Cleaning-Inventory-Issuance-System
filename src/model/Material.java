package model;

public class Material {
    private int id;
    private String name;
    private int quantity;
    private int reorderLevel;

    public Material(int id, String name, int quantity, int reorderLevel) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }
}