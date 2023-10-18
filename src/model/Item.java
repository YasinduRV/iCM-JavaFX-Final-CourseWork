package model;

public class Item {
    private String id;
    private String description;
    private String type;
    private String suppId;
    private double UnitPrice;
    private int qtyOnHand;
    private String size;
    private double discount;

    public Item(String id, String description, String type, String suppId, double unitPrice, int qtyOnHand, String size, double discount) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.suppId = suppId;
        UnitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.size = size;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuppId() {
        return suppId;
    }

    public void setSuppId(String suppId) {
        this.suppId = suppId;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
