package model;

public class OrderDetails {
    private String orderID;
    private String intemCode;
    private String description;
    private int qty;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private double unitPrice;
    private String type;
    private String size;
    private double discount;
    private double price;

    public OrderDetails(String orderID, String intemCode, String description, int qty, double price) {
        this.orderID = orderID;
        this.intemCode = intemCode;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public OrderDetails(String orderID, String intemCode, String description, int qty, double unitPrice, String type, String size, double discount, double price) {
        this.orderID = orderID;
        this.intemCode = intemCode;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.type = type;
        this.size = size;
        this.discount = discount;
        this.price = price;
    }

    public OrderDetails(String orderID, String intemCode, int qty, double unitPrice, String type, String size, double discount, double price) {
        this.orderID = orderID;
        this.intemCode = intemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.type = type;
        this.size = size;
        this.discount = discount;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getIntemCode() {
        return intemCode;
    }

    public void setIntemCode(String intemCode) {
        this.intemCode = intemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
